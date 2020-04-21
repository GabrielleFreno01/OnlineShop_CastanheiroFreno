package com.android.onlineshop_castanheirofreno.viewmodel.item;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.onlineshop_castanheirofreno.BaseApp;
import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.pojo.CategoryWithItems;
import com.android.onlineshop_castanheirofreno.database.repository.CategoryRepository;
import com.android.onlineshop_castanheirofreno.database.repository.ItemRepository;
import com.android.onlineshop_castanheirofreno.database.repository.OrderRepository;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private Application application;

    private ItemRepository repository;

    private CategoryRepository catRepository;

    private OrderRepository orderRepository;

    private String idItem;
    private String idCategory;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<ItemEntity> observableItem;
    private final MediatorLiveData<List<ItemEntity>> observableItems;
    private final MediatorLiveData<List<CategoryEntity>> observableCategories;
    private final MediatorLiveData<List<ItemEntity>> observableNewItems;
    private final MediatorLiveData<CategoryWithItems> observableCategoryWithItems;


    public ItemViewModel(@NonNull Application application,
                         final String idItem, final String categoryId, ItemRepository itemRepository,
                         CategoryRepository catRepository, OrderRepository orderRepository) {
        super(application);

        this.application = application;
        this.idItem = idItem;
        this.idCategory = categoryId;

        repository = itemRepository;
        this.catRepository = catRepository;
        this.orderRepository = orderRepository;

        observableItem = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableItem.setValue(null);

        observableCategories = new MediatorLiveData<>();
        observableCategories.setValue(null);

        observableItems = new MediatorLiveData<>();
        observableItems.setValue(null);

        observableNewItems = new MediatorLiveData<>();
        observableNewItems.setValue(null);

        observableCategoryWithItems = new MediatorLiveData<>();
        observableCategoryWithItems.setValue(null);

        LiveData<ItemEntity> item = repository.getItem(application, idItem, categoryId);
        LiveData<List<CategoryEntity>> listCategory = catRepository.getCategories(application);
        LiveData<CategoryWithItems> categoryWithItems = catRepository.getCategoryWithItems(application, categoryId);
        LiveData<List<ItemEntity>> listNewItems = repository.getNewItems(application);

        // observe the changes of the account entity from the database and forward them
        observableItem.addSource(item, observableItem::setValue);
        observableCategories.addSource(listCategory, observableCategories::setValue);
        observableCategoryWithItems.addSource(categoryWithItems, observableCategoryWithItems::setValue);
        observableNewItems.addSource(listNewItems, observableNewItems::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String itemId;

        private final String categoryId;

        private final ItemRepository repository;

        private final CategoryRepository catRepository;

        private final OrderRepository orderRepository;

        public Factory(@NonNull Application application, String itemId, String categoryId) {
            this.application = application;
            this.itemId = itemId;
            this.categoryId = categoryId;
            repository = ((BaseApp) application).getItemRepository();
            catRepository = ((BaseApp) application).getCategoryRepository();
            orderRepository = ((BaseApp) application).getOrderRepository();
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ItemViewModel(application, itemId, categoryId, repository, catRepository, orderRepository);
        }
    }

    public LiveData<ItemEntity> getItem() {
        return observableItem;
    }

    public LiveData<List<CategoryEntity>> getCategories() {
        return observableCategories;
    }

    public LiveData<CategoryWithItems> getCategoryWithItems() { return observableCategoryWithItems; }

    public LiveData<List<ItemEntity>> getNewItems() {
        return observableNewItems;
    }


    public void deleteItem(ItemEntity item, OnAsyncEventListener callback) {
        //Delete the item
        repository.delete(item, callback);
        //Delete all the orders containing this item too
        orderRepository.deleteCorrespondingOrders(item,callback);
    }

    public String createItem(ItemEntity item, OnAsyncEventListener callback) {
        return repository.insert(item, callback);
    }

    public void updateItem(ItemEntity newItem, ItemEntity oldItem, OnAsyncEventListener callback) {
        repository.update(newItem, oldItem, callback);
        //Check if the category has changed
        if(!newItem.getIdCategory().equals(oldItem.getIdCategory())) {
            //If it has changed, change the category in the orders too
            orderRepository.changeCategory(newItem, callback);
        }
    }
    public void createOrder(OrderEntity newOrder, OnAsyncEventListener callback) {
        orderRepository.insert(newOrder, callback);
    }
}