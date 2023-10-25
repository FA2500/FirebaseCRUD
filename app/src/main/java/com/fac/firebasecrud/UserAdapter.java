package com.fac.firebasecrud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    //E3
    private List<User> userList;
    private Context context;

    public UserAdapter(Context context, List<User> userList) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_ui,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.username.setText("Username = "+userList.get(position).getUsername());
        holder.age.setText("Age = "+userList.get(position).getAge());
        holder.saving.setText("Saving = RM"+userList.get(position).getSaving());
        holder.married.setText("Married = "+(userList.get(position).isMarried()?"Married":"Single"));

        //E4
        holder.EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof MainActivity)
                {
                    ((MainActivity)context).clickEdit(userList.get(position));
                }
            }
        });

        holder.DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof MainActivity)
                {
                    ((MainActivity)context).clickDelete(userList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username, age, saving, married;

        //E4
        private Button EditBtn, DeleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameTV);
            age = itemView.findViewById(R.id.ageTV);
            saving = itemView.findViewById(R.id.savingTV);
            married = itemView.findViewById(R.id.marriedTV);
            EditBtn = itemView.findViewById(R.id.editBtn);
            DeleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
