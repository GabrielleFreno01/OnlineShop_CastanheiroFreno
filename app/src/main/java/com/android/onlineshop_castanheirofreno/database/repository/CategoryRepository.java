package com.android.onlineshop_castanheirofreno.database.repository;

public class CategoryRepository {

    private static CategoryRepository instance;

    public CategoryRepository() {
    }

    public static CategoryRepository getInstance() {
        if (instance == null) {
            synchronized (CategoryRepository.class) {
                if (instance == null) {
                    instance = new CategoryRepository();
                }
            }
        }
        return instance;
    }
/*
    public LiveData<List<CategoryEntity>> getCategories(Application application) {
        LiveData<List<CategoryEntity>> category = ((BaseApp) application).getDatabase().categoryDao().getAll();
        //System.out.println(category.getValue().get(1));
        return category;
    }

    public LiveData<CategoryEntity> getCategory(final Long categoryId, Application application) {
        return ((BaseApp) application).getDatabase().categoryDao().getById(categoryId);
    }*/

}
