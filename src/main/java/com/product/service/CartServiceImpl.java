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
import org.springframework.validation.Errors;

import com.product.costant.ErrorCodeEnum;
import com.product.data.DataProvider;
import com.product.entity.Cart;
import com.product.entity.Catalogue;
import com.product.entity.ChannelEnum;
import com.product.entity.ProductItem;
import com.product.entity.ProductItemStatusEnum;
import com.product.entity.User;
import com.product.exception.ErrorResponseException;
import com.product.exception.InputParameterException;
import com.product.model.request.AddProductItemReq;
import com.product.model.response.CartResp;
import com.product.model.response.CatalogueResp;
import com.product.model.response.ProductItemResp;
import com.product.model.response.UserResp;
import com.product.repository.CartRepository;
import com.product.repository.CatalogueRepository;
import com.product.repository.ProductItemRepository;
import com.product.util.TimeUtils;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private DataProvider dataProvider;

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public CartResp addOrUpdate(Long userId) throws ErrorResponseException {
        User user = dataProvider.addOrSaveUser(userId);

        Cart cart = new Cart();
        cart.setChannel(ChannelEnum.CHANNEL_1);
        cart.setCurrency(dataProvider.getByCurrencyCode("CNY"));
        cart.setUser(user);
        cart.setCreatedAt(TimeUtils.getUtcTime());
        cart.setUpdatedAt(TimeUtils.getUtcTime());
        Cart cartReturn = dataProvider.addOrUpdateCart(user, cart);

        return constructCartResp(cartReturn.getCartId());
    }

    @Override
    public CartResp getByCartId(Long cartId) throws ErrorResponseException {
        return constructCartResp(cartId);
    }

    private CartResp constructCartResp(Long cartId) throws ErrorResponseException {
        Cart cart = (Cart) cartRepository.findByCartId(cartId).orElseThrow(() -> new ErrorResponseException(ErrorCodeEnum.CART_NOT_FOUND.getSelfDefinedCode(),
                ErrorCodeEnum.CART_NOT_FOUND.getMessage(), ErrorCodeEnum.CART_NOT_FOUND.getDetail()));
        CartResp cartResp = new CartResp();
        List<ProductItemResp> productItemRespList = new LinkedList<ProductItemResp>();
        productItemRepository.findByCart(cart).forEach(x -> {
            if (x.getStatus().equals(ProductItemStatusEnum.NOT_BOOKED)) {
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

        cartResp.setCartId(cart.getCartId());
        cartResp.setChannel(cart.getChannel().toString());
        cartResp.setCurrency(cart.getCurrency().getCurrencyCode());

        UserResp userResp = new UserResp();
        userResp.setUserId(cart.getUser().getUserId());
        userResp.setFirstname(cart.getUser().getFirstName());
        userResp.setLastname(cart.getUser().getLastName());
        cartResp.setUser(userResp);
        cartResp.setProductItemRespList(productItemRespList);
        cartResp.setQuantity(cart.getQuantity());
        cartResp.setStatus(cart.getStatus().toString());
        cartResp.setTotalAmount(cart.getTotalAmount());
        return cartResp;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public CartResp addProductItem(AddProductItemReq addProductItemReq) throws ErrorResponseException {
        LocalDateTime currentTime = TimeUtils.getUtcTime();
        ProductItem productItem = new ProductItem();
        Cart cart = (Cart) cartRepository.findByCartId(addProductItemReq.getCartId())
                .orElseThrow(() -> new ErrorResponseException(ErrorCodeEnum.CART_NOT_FOUND.getSelfDefinedCode(), ErrorCodeEnum.CART_NOT_FOUND.getMessage(),
                        ErrorCodeEnum.CART_NOT_FOUND.getDetail()));
        Catalogue catalogue = (Catalogue) catalogueRepository.findByCatalogueId(addProductItemReq.getCatalogueId())
                .orElseThrow(() -> new ErrorResponseException(ErrorCodeEnum.CATALOGUE_NOT_FOUND.getSelfDefinedCode(), ErrorCodeEnum.CATALOGUE_NOT_FOUND.getMessage(),
                        ErrorCodeEnum.CATALOGUE_NOT_FOUND.getDetail()));
        productItem.setChannel(ChannelEnum.valueOf(addProductItemReq.getChannel()));
        productItem.setCurrency(dataProvider.getByCurrencyCode(addProductItemReq.getCurrency()));
        productItem.setQuantity(addProductItemReq.getQuantity());
        productItem.setStatus(ProductItemStatusEnum.valueOf(addProductItemReq.getStatus()));
        productItem.setTotalAmount(addProductItemReq.getTotalAmount());
        productItem.setCart(cart);
        productItem.setCatalogue(catalogue);
        productItem.setCreatedAt(currentTime);
        productItem.setUpdatedAt(currentTime);
        productItemRepository.save(productItem);

        cart.setQuantity(cart.getQuantity() + productItem.getQuantity());
        cart.setTotalAmount(cart.getTotalAmount().add(productItem.getTotalAmount()));
        cart.setUpdatedAt(currentTime);
        cartRepository.save(cart);

        catalogue.setQuantity(catalogue.getQuantity() - productItem.getQuantity());
        catalogue.setUpdatedAt(currentTime);
        catalogueRepository.save(catalogue);

        return constructCartResp(cart.getCartId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public CartResp deleteProductItem(Long productItemId) throws ErrorResponseException {
        ProductItem productItem = (ProductItem) productItemRepository.findByProductItemId(productItemId)
                .orElseThrow(() -> new ErrorResponseException(ErrorCodeEnum.PRODUCT_ITEM_NOT_FOUND.getSelfDefinedCode(), ErrorCodeEnum.PRODUCT_ITEM_NOT_FOUND.getMessage(),
                        ErrorCodeEnum.PRODUCT_ITEM_NOT_FOUND.getDetail()));
        productItem.setStatus(ProductItemStatusEnum.DELETED);
        productItemRepository.save(productItem);

        Cart cart = (Cart) cartRepository.findByCartId(productItem.getCart().getCartId())
                .orElseThrow(() -> new ErrorResponseException(ErrorCodeEnum.CART_NOT_FOUND.getSelfDefinedCode(), ErrorCodeEnum.CART_NOT_FOUND.getMessage(),
                        ErrorCodeEnum.CART_NOT_FOUND.getDetail()));
        Catalogue catalogue = (Catalogue) catalogueRepository.findByCatalogueId(productItem.getCatalogue().getCatalogueId())
                .orElseThrow(() -> new ErrorResponseException(ErrorCodeEnum.CATALOGUE_NOT_FOUND.getSelfDefinedCode(), ErrorCodeEnum.CATALOGUE_NOT_FOUND.getMessage(),
                        ErrorCodeEnum.CATALOGUE_NOT_FOUND.getDetail()));

        cart.setQuantity(cart.getQuantity() - productItem.getQuantity());
        cart.setTotalAmount(cart.getTotalAmount().subtract(productItem.getTotalAmount()));
        cartRepository.save(cart);

        catalogue.setQuantity(catalogue.getQuantity() + productItem.getQuantity());
        catalogueRepository.save(catalogue);

        return constructCartResp(productItem.getCart().getCartId());
    }

    @Override
    public void validateInboundRequest(Errors errors) throws InputParameterException {
        if (errors.hasErrors()) {
            String errorsMsg = errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).reduce((join, x) -> join + ", " + x).get();
            logger.error("errorCode: " + ErrorCodeEnum.INCOMPLETE_REQUEST_BODY.getSelfDefinedCode());
            throw new InputParameterException(ErrorCodeEnum.INCOMPLETE_REQUEST_BODY.getSelfDefinedCode(), ErrorCodeEnum.INCOMPLETE_REQUEST_BODY.getMessage(), errorsMsg);
        }
    }

}
