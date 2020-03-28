package com.android.onlineshop_castanheirofreno.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "orders",
        foreignKeys =
        @ForeignKey(
                entity = CustomerEntity.class,
                parentColumns = "email",
                childColumns = "owner",
                onDelete = ForeignKey.CASCADE
        )
)

public class OrderEntity {

        @PrimaryKey(autoGenerate = true)
        private int idOrder;

        @ColumnInfo(name = "price")
        private double price;

        @ColumnInfo(name = "creation_date")
        private String creation_date;

        @ColumnInfo(name = "idItem")
        private long idItem;

        @ColumnInfo(name = "owner")
        private String owner;

        @ColumnInfo(name = "status")
        private String status;

        @ColumnInfo(name = "delivery_date")
        private String delivery_date;

        @Ignore
        public OrderEntity() {
        }

        public OrderEntity(double price, String creation_date,String delivery_date, long idItem, String status, String owner) {
                this.price = price;
                this.creation_date = creation_date;
                this.delivery_date = delivery_date;
                this.idItem = idItem;
                this.status = status;
                this.owner = owner;

        }

        public int getIdOrder() {
                return idOrder;
        }

        public void setIdOrder(int id) {
                this.idOrder = id;
        }

        public double getPrice() { return price; }

        public void setPrice(double price) { this.price = price; }

        public String getCreation_date() { return creation_date; }

        public void setCreation_date(String creation_date) { this.creation_date = creation_date; }

        public long getIdItem() { return idItem; }

        public void setIdItem(long idItem) { this.idItem = idItem; }

        public String getStatus() { return status; }

        public void setStatus(String status) { this.status = status; }

        public String getDelivery_date() { return delivery_date; }

        public void setDelivery_date(String delivery_date) { this.delivery_date = delivery_date; }

        public String getOwner() { return owner; }

        public void setOwner(String owner) { this.owner = owner; }



}
