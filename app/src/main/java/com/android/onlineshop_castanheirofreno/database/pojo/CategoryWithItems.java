package com.android.onlineshop_castanheirofreno.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;

import java.util.List;

public class CategoryWithItems {

    @Embedded
    public CategoryEntity category ;

    @Relation(parentColumn = "id", entityColumn = "categoryId", entity = ItemEntity.class)
    public List<ItemEntity> items;
}
