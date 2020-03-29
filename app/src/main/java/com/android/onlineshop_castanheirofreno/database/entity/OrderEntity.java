package com.android.onlineshop_castanheirofreno.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "orders",
        foreignKeys = {
        @ForeignKey(
                entity = CustomerEntity.class,
                parentColumns = "email",
                childColumns = "owner",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = ItemEntity.class,
                parentColumns = "idItem",
                childColumns = "idItem",
                onDelete = ForeignKey.CASCADE
        )},
        indices = {
        @Index(value = {"owner"}),
        @Index(value = {"idItem"})
        }
)
public class OrderEntity implements Comparable{

        @PrimaryKey(autoGenerate = true)
        private long idOrder;

        @ColumnInfo(name = "price")
        private double price;

        @ColumnInfo(name = "creation_date")
        private String creationDate;

        @ColumnInfo(name = "idItem")
        private long idItem;

        @ColumnInfo(name = "owner")
        private String owner;

        @ColumnInfo(name = "status")
        private String status;

        @ColumnInfo(name = "delivery_date")
        private String deliveryDate;

        @Ignore
        public OrderEntity() {
        }

        public OrderEntity(double price, String creationDate,String deliveryDate, long idItem, String status, String owner) {
                this.price = price;
                this.creationDate = creationDate;
                this.deliveryDate = deliveryDate;
                this.idItem = idItem;
                this.status = status;
                this.owner = owner;

        }

        public long getIdOrder() {
                return idOrder;
        }

        public void setIdOrder(long id) {
                this.idOrder = id;
        }

        public double getPrice() { return price; }

        public void setPrice(double price) { this.price = price; }

        public String getCreationDate() { return creationDate; }

        public void setCreationDate(String creationDate) { this.creationDate = creationDate; }

        public long getIdItem() { return idItem; }

        public void setIdItem(long idItem) { this.idItem = idItem; }

        public String getStatus() { return status; }

        public void setStatus(String status) { this.status = status; }

        public String getDeliveryDate() { return deliveryDate; }

        public void setDeliveryDate(String deliveryDate) { this.deliveryDate = deliveryDate; }

        public String getOwner() { return owner; }

        public void setOwner(String owner) { this.owner = owner; }

        @Override
        public String toString() {
                return idOrder + " " + price;
        }

        @Override
        public int compareTo(@NonNull Object o) {
                return toString().compareTo(o.toString());
        }



}
