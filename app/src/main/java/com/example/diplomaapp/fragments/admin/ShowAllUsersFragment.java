package com.example.diplomaapp.fragments.admin;

import static com.example.diplomaapp.api.AuthToken.createAuthToken;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplomaapp.R;
import com.example.diplomaapp.adapters.UserAdapter;
import com.example.diplomaapp.api.NetworkService;
import com.example.diplomaapp.api.UserApi;
import com.example.diplomaapp.entity.User;
import com.example.diplomaapp.test.ClickInterface;
import com.example.diplomaapp.test.RecyclerItemClickListener;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAllUsersFragment extends Fragment implements ClickInterface {

    public ShowAllUsersFragment(){
        super(R.layout.fragment_list_users);
    }
    private RecyclerView rvUsers;
    private UserAdapter adapter;
    private List<User> users;

    private String password;
    private String username;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            password = bundle.getString("password");

        }

        rvUsers = view.findViewById(R.id.rvList);

        fillAdapter();

        rvUsers.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), rvUsers ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Fragment Fragment_User_Info   =  new FullInfoUserFragement();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, Fragment_User_Info, "TAG")
                                .commit();
                        User user = users.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", username);
                        bundle.putString("password", password);
                        bundle.putParcelable("user",  user);  // Key, value
                        Fragment_User_Info.setArguments(bundle);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // this method is called
                // when the item is moved.
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                User deletedCourse = users.get(viewHolder.getAdapterPosition());
                deleteUser(deletedCourse);
                // below line is to get the position
                // of the item at that position.
                int position = viewHolder.getAdapterPosition();

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                users.remove(viewHolder.getAdapterPosition());

                // below line is to notify our item is removed from adapter.
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                // below line is to display our snackbar with action.

            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(rvUsers);
    }










    public void deleteUser(User user){


        String auth = createAuthToken(username, password);
        NetworkService networkService = NetworkService.getInstance();
        UserApi api = networkService.getUserApi();
        Call<ResponseBody> call = api.deleteUser(auth, user.getId());


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
                System.out.println(response.code());
                System.out.println(response.body());
                System.out.println(user.getId());
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // handle failure
                Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });;
    }


    private void fillAdapter(){

        String auth = createAuthToken(username, password);

        NetworkService networkService = NetworkService.getInstance();
        UserApi api = networkService.getUserApi();

        Call<List<User>> call = api.getAllUsers(auth);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                users = response.body();
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                rvUsers.setHasFixedSize(true);
                rvUsers.setLayoutManager(manager);
                adapter = new UserAdapter(getContext(), users);
                rvUsers.setAdapter(adapter);
                System.out.println("test!!!!!!!");
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }


    @Override
    public void recyclerviewOnClick(int position) {

    }
}
