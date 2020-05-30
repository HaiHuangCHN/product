package com.product.service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.product.costant.ErrorCodeEnum;
import com.product.data.DataProvider;
import com.product.entity.Cart;
import com.product.entity.ChannelEnum;
import com.product.entity.OrderDetail;
import com.product.entity.OrderDetailPaymentPlatformEnum;
import com.product.entity.OrderDetailPaymentStatusEnum;
import com.product.entity.OrderDetailStatusEnum;
import com.product.entity.ProductItem;
import com.product.entity.ProductItemStatusEnum;
import com.product.exception.ErrorResponseException;
import com.product.model.request.BookOrderReq;
import com.product.model.request.CreateOrderReq;
import com.product.model.response.CatalogueResp;
import com.product.model.response.OrderDetailResp;
import com.product.model.response.ProductItemResp;
import com.product.repository.CartRepository;
import com.product.repository.OrderDetailRepository;
import com.product.repository.ProductItemRepository;
import com.product.util.TimeUtils;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailServiceImpl.class);

    @Autowired
    private DataProvider dataProvider;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public OrderDetailResp findByOrderDetailId(Long orderDetailId) throws ErrorResponseException {
        return constructOrderDetailResp(orderDetailId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public OrderDetailResp createOrder(CreateOrderReq createOrderReq) throws ErrorResponseException {
        LocalDateTime currentTime = TimeUtils.getUtcTime();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setStatus(OrderDetailStatusEnum.IN_PROGRESS);
        orderDetail.setUser(dataProvider.addOrSaveUser(createOrderReq.getUserId()));
        orderDetail.setChannel(ChannelEnum.valueOf(createOrderReq.getChannel()));
        orderDetail.setPaymentPlatform(OrderDetailPaymentPlatformEnum.valueOf(createOrderReq.getPlatform()));
        orderDetail.setTotalAmount(createOrderReq.getTotalAmount());
        orderDetail.setCurrency(dataProvider.getByCurrencyCode(createOrderReq.getCurrency()));
        orderDetail.setCreatedAt(currentTime);
        orderDetail.setUpdatedAt(currentTime);
        OrderDetail orderDetailSaved = orderDetailRepository.save(orderDetail);

        for (Long productItemId : createOrderReq.getProductItemIdList()) {
            ProductItem productItem = (ProductItem) productItemRepository.findByProductItemId(productItemId)
                    .orElseThrow(() -> new ErrorResponseException(ErrorCodeEnum.ORDER_NOT_FOUND.getSelfDefinedCode(), ErrorCodeEnum.ORDER_NOT_FOUND.getMessage(),
                            ErrorCodeEnum.PRODUCT_NOT_FOUND.getDetail()));
            productItem.setOrderDetail(orderDetailSaved);
            productItem.setStatus(ProductItemStatusEnum.BOOKING);
            productItem.setUpdatedAt(currentTime);
            productItemRepository.save(productItem);

            Cart cart = (Cart) cartRepository.findByCartId(productItem.getCart().getCartId())
                    .orElseThrow(() -> new ErrorResponseException(ErrorCodeEnum.CART_NOT_FOUND.getSelfDefinedCode(), ErrorCodeEnum.CART_NOT_FOUND.getMessage(),
                            ErrorCodeEnum.CART_NOT_FOUND.getDetail()));
            cart.setQuantity(cart.getQuantity() - productItem.getQuantity());
            cart.setTotalAmount(cart.getTotalAmount().subtract(productItem.getTotalAmount()));
            cart.setUpdatedAt(currentTime);
            cartRepository.save(cart);

        }

        return constructOrderDetailResp(orderDetailSaved.getOrderDetailId());
    }

    private OrderDetailResp constructOrderDetailResp(Long orderDetailId) throws ErrorResponseException {
        OrderDetail orderDetail = (OrderDetail) orderDetailRepository.findByOrderDetailId(orderDetailId)
                .orElseThrow(() -> new ErrorResponseException(ErrorCodeEnum.ORDER_NOT_FOUND.getSelfDefinedCode(), ErrorCodeEnum.ORDER_NOT_FOUND.getMessage(),
                        ErrorCodeEnum.ORDER_NOT_FOUND.getDetail()));
        OrderDetailResp orderDetailResp = new OrderDetailResp();
        orderDetailResp.setOrderDetailId(orderDetail.getOrderDetailId());
        orderDetailResp.setTotalAmount(orderDetail.getTotalAmount());
        orderDetailResp.setCurrency(orderDetail.getCurrency().getCurrencyCode());
        orderDetailResp.setChannel(orderDetail.getChannel().toString());
        orderDetailResp.setPaymentAuthorizedAmount(orderDetail.getPaymentAuthorizedAmount());
        orderDetailResp.setPaymentAuthorizedCurrency(orderDetail.getPaymentAuthorizedCurrency());
        orderDetailResp.setPaymentMethod(orderDetail.getPaymentMethod());
        orderDetailResp.setPaymentPlatform(orderDetail.getPaymentPlatform().toString());
        orderDetailResp.setPaymentMethod(orderDetail.getPaymentMethod());
        orderDetailResp.setPaymentProvider(orderDetail.getPaymentProvider());
        orderDetailResp.setStatus(orderDetail.getStatus().toString());

        List<ProductItemResp> productItemRespList = new LinkedList<ProductItemResp>();
        productItemRepository.findByOrderDetail(orderDetail).forEach(x -> {
            if (x.getStatus().equals(ProductItemStatusEnum.BOOKED) || x.getStatus().equals(ProductItemStatusEnum.BOOKING)) {
                ProductItemResp productItemResp = new ProductItemResp();
                productItemResp.setProductItemId(x.getProductItemId());
                productItemResp.setStatus(x.getStatus().toString());
                productItemResp.setChannel(x.getChannel().toString());
                productItemResp.setQuantity(x.getQuantity());
                productItemResp.setTotalAmount(x.getTotalAmount());
                productItemResp.setCurrency(x.getCurrency().getCurrencyCode());

                CatalogueResp catalogueResp = new CatalogueResp();
                catalogueResp.setCatalogueId(x.getCatalogue().getCatalogueId());
                catalogueResp.setDisplayName(x.getCatalogue().getDisplayName());
                catalogueResp.setCatagory(x.getCatalogue().getCatagory().toString());
                catalogueResp.setPrice(x.getCatalogue().getPrice());
                catalogueResp.setCurrency(x.getCatalogue().getCurrency().getCurrencyCode());
                catalogueResp.setStatus(x.getCatalogue().getStatus().toString());
                catalogueResp.setQuantity(x.getCatalogue().getQuantity());
                catalogueResp.setShortDesc(x.getCatalogue().getShortDesc());
                catalogueResp.setLongDesc(x.getCatalogue().getLongDesc());

                productItemResp.setCatalogueResp(catalogueResp);
                productItemRespList.add(productItemResp);
            }
        });

        orderDetailResp.setProductItemRespList(productItemRespList);

        return orderDetailResp;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public OrderDetailResp updateOrder(BookOrderReq bookOrderReq) throws ErrorResponseException {
        OrderDetail orderDetail = (OrderDetail) orderDetailRepository.findByOrderDetailId(bookOrderReq.getOrderDetailId())
                .orElseThrow(() -> new ErrorResponseException(ErrorCodeEnum.ORDER_NOT_FOUND.getSelfDefinedCode(), ErrorCodeEnum.ORDER_NOT_FOUND.getMessage(),
                        ErrorCodeEnum.ORDER_NOT_FOUND.getDetail()));
        orderDetail.setPaymentAuthorizedAmount(bookOrderReq.getPaymentAuthorizedAmount());
        orderDetail.setPaymentAuthorizedCurrency(bookOrderReq.getPaymentAuthorizedCurrency());
        orderDetail.setPaymentMethod(bookOrderReq.getPaymentMethod());
        orderDetail.setPaymentPlatform(bookOrderReq.getPaymentPlatform());
        orderDetail.setPaymentProvider(bookOrderReq.getPaymentProvider());
        orderDetail.setPaymentStatus(bookOrderReq.getPaymentStatus());
        if (OrderDetailPaymentStatusEnum.AUTH.equals(bookOrderReq.getPaymentStatus())) {
            productItemRepository.findByOrderDetail(orderDetail).forEach(x -> {
                if (x.getStatus().equals(ProductItemStatusEnum.BOOKING)) {
                    x.setStatus(ProductItemStatusEnum.BOOKED);
                }
                productItemRepository.save(x);
            });
        }

        orderDetailRepository.save(orderDetail);
        return constructOrderDetailResp(orderDetail.getOrderDetailId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void deleteByOrderDetailId(Long orderDetailId) throws ErrorResponseException {
        // TODO Auto-generated method stub
        // TODO when delete order, the related product items should be deleted as well

    }

}
