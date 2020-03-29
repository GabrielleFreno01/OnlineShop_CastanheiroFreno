package com.android.onlineshop_castanheirofreno.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;


public class OrderWithItem {
    @Embedded
    public OrderEntity order;

    @Relation(parentColumn = "idItem", entityColumn = "idItem", entity = ItemEntity.class)
    public ItemEntity item;
}
