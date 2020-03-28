package com.android.onlineshop_castanheirofreno.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;

import java.util.List;

public class CustomerWithOrders {
    @Embedded
    public CustomerEntity client;

    @Relation(parentColumn = "email", entityColumn = "owner", entity = OrderEntity.class)
    public List<OrderEntity> orders;
}