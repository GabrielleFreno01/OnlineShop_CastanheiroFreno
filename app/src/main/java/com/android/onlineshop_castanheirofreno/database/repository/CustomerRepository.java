package com.android.onlineshop_castanheirofreno.database.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.firebase.CustomerLiveData;
import com.android.onlineshop_castanheirofreno.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerRepository {

    private static CustomerRepository instance;

    private static final String TAG = "CustomerRepository";

    private CustomerRepository() {
    }

    public static CustomerRepository getInstance() {
        if (instance == null) {
            synchronized (OrderRepository.class) {
                if (instance == null) {
                    instance = new CustomerRepository();
                }
            }
        }
        return instance;
    }

    public void signIn(final String email, final String password,
                       final OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    public LiveData<CustomerEntity> getCustomer(final String clientId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("customers")
                .child(clientId); //"O1OuWOo1y1Ow2bdAGm0bqGxuuID2"
        return new CustomerLiveData(reference);
    }

    /*public LiveData<List<CustomerWithOrders>> getCustomerWithOrders(final String owner) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Customers");
        return new CustomerWithOrders(reference, owner);
    }*/

    public void register(final CustomerEntity customer, final OnAsyncEventListener callback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                customer.getEmail(),
                customer.getPassword()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                customer.setIdCustomer(FirebaseAuth.getInstance().getCurrentUser().getUid());
                insert(customer, callback);
            } else {
                callback.onFailure(task.getException());
            }
        });
    }

    private void insert(final CustomerEntity customer, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("customers")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(customer, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        callback.onFailure(null);
                                        Log.d(TAG, "Rollback successful: User account deleted");
                                    } else {
                                        callback.onFailure(task.getException());
                                        Log.d(TAG, "Rollback failed: signInWithEmail:failure",
                                                task.getException());
                                    }
                                });
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final CustomerEntity customer, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("customers")
                .child(customer.getIdCustomer())
                .updateChildren(customer.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });

    }

    public void updatePwd(final CustomerEntity customer, final OnAsyncEventListener callback){
        FirebaseAuth.getInstance().getCurrentUser().updatePassword(customer.getPassword())
                .addOnFailureListener(
                        e -> Log.d(TAG, "updatePassword failure!", e)
                );
    }

    public void delete(final CustomerEntity customer, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("customers")
                .child(customer.getIdCustomer())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
/*
    public LiveData<CustomerEntity> getCustomer(final long customerId, Application application) {
        return ((BaseApp) application).getDatabase().customerDao().getById(customerId);
    }

    public LiveData<CustomerEntity> getCustomerUser(final String user, Application application) {
        return ((BaseApp) application).getDatabase().customerDao().getByEmail(user);
    }

    public LiveData<CustomerEntity> getCustomerByEmail(final String email, Application application) {
        return ((BaseApp) application).getDatabase().customerDao().getByEmail(email);
    }

    public LiveData<List<CustomerWithOrders>> getCustomerWithOrders(final String owner,
                                                                    Application application) {
        return ((BaseApp) application).getDatabase().customerDao().getOtherCustomersWithOrders(owner);
    }

    public void insert(final CustomerEntity customer, OnAsyncEventListener callback,
                       Application application) {
        new CreateCustomer(application, callback).execute(customer);
    }

    public void update(final CustomerEntity customer, OnAsyncEventListener callback,
                       Application application) {
        new UpdateCustomer(application, callback).execute(customer);
    }

    public void delete(final CustomerEntity customer, OnAsyncEventListener callback,
                       Application application) {
        new DeleteCustomer(application, callback).execute(customer);
    }*/


}
