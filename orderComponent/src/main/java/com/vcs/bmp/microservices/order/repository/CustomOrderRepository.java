package com.vcs.bmp.microservices.order.repository;

import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import  com.broadleafcommerce.data.tracking.jpa.filtering.narrow.JpaNarrowingHelper;
import com.broadleafcommerce.data.tracking.jpa.filtering.narrow.factory.JpaTrackableRepositoryDelegateHelper;
import com.broadleafcommerce.order.provider.jpa.domain.JpaOrder;
import com.broadleafcommerce.order.provider.jpa.domain.JpaOrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomOrderRepository implements ICustomOrderRepository {


    private final JpaTrackableRepositoryDelegateHelper<JpaOrder> helper;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long countOrdersByCustomerIdAndAccountIdAndOrderItemsSkuIn(
            String customerId,
            String accountId,
            List<String> itemSkus,
            @Nullable ContextInfo contextInfo) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<JpaOrder> root = query.from(JpaOrder.class);
        Join<JpaOrder, JpaOrderItem> itemJoin = root.join("orderItems", JoinType.INNER);

        Map<String, Object> params = new HashMap<>();

        Predicate customerPredicate =
                builder.equal(root.get("customerId"),
                        builder.parameter(String.class, "customerId"));

        Predicate accountPredicate =
                builder.equal(root.get("accountId"),
                        builder.parameter(String.class, "accountId"));

        Predicate skuPredicate = itemJoin.get("sku").in(itemSkus);

        params.put("customerId", customerId);
        params.put("accountId", accountId);

        query.select(builder.countDistinct(root))
                .where(builder.and(customerPredicate, accountPredicate, skuPredicate));

        return helper.getHelper().count(
                new JpaNarrowingHelper.JpaCriterias<>(query, null, params),
                JpaOrder.class,
                contextInfo);
    }
}