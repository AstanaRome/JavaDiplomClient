package com.example.diplomaapp.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.diplomaapp.R;
import com.example.diplomaapp.entity.Role;
import com.example.diplomaapp.entity.User;


import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<User> users;

    public UserAdapter(Context context, List<User> users) {
        this.users = users;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.usernameView.setText(user.getUserName());
        holder.fullnameView.setText(user.getFullName());
        String roles = "";
        for (Role role: user.getRoles() ) {
            roles +=" " + role.getName();
        }
        holder.roleView.setText(roles);
        holder.avaView.setImageResource(R.drawable.avatar);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView usernameView, fullnameView, roleView;
        final ImageView avaView;
        ViewHolder(View view){
            super(view);
            usernameView = view.findViewById(R.id.tvUsername);
            fullnameView = view.findViewById(R.id.tvFullname);
            roleView = view.findViewById(R.id.tvRole);
            avaView = view.findViewById(R.id.imageView2);

        }
    }



}