package com.android.onlineshop_castanheirofreno.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.util.RecyclerViewItemClickListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;


public class NewItemsAdapter extends RecyclerView.Adapter<NewItemsAdapter.ItemsViewHolder> {

    private Context context;
    private List<ItemEntity> items;
    private RecyclerViewItemClickListener myListener;

    public NewItemsAdapter(Context context, List<ItemEntity> items, RecyclerViewItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.myListener = listener;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_new_item, parent, false);
        ItemsViewHolder holder = new ItemsViewHolder(view);
        view.setOnClickListener(v -> myListener.onItemClick(view, holder.getAdapterPosition()));
        view.setOnLongClickListener(v -> {
            myListener.onItemLongClick(view, holder.getAdapterPosition());
            return true;
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        ItemEntity itemEntity = items.get(position);

        /*String name = itemEntity.getTag();
        int resId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        holder.imageView.setImageResource(resId);*/

        NumberFormat defaultFormat = new DecimalFormat("#0.00");
        holder.price_textView.setText("CHF " + defaultFormat.format(itemEntity.getPrice()));
        holder.name_textView.setText(itemEntity.getName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ItemsViewHolder extends RecyclerView.ViewHolder{
        TextView name_textView;
        TextView price_textView;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            name_textView = itemView.findViewById(R.id.new_item_name);
            price_textView = itemView.findViewById(R.id.new_item_price);
        }
    }

    public void setData(final List<ItemEntity> data) {
        if (items == null) {
            items = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return items.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if(((ItemEntity) items.get(oldItemPosition)).getIdItem()==((ItemEntity) data.get(newItemPosition)).getIdItem())
                        return true;
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    ItemEntity newItem = (ItemEntity) data.get(newItemPosition);
                    ItemEntity oldItem = (ItemEntity) items.get(newItemPosition);
                    return newItem.getIdItem()== oldItem.getIdItem()
                            && newItem.getName() == oldItem.getName()
                            && newItem.getPrice() == oldItem.getPrice()
                            && newItem.getIdCategory() == oldItem.getIdCategory()
                            && newItem.getDescription() == oldItem.getDescription();
                }
            });
            items = data;
            result.dispatchUpdatesTo(this);
        }
    }

}