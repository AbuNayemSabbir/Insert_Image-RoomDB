package com.example.roomdatabaseimageinsert;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    AppDatabase db;
    List<UserModel> users;
    Context ctx;
    LayoutInflater inflater;

    public Adapter(List<UserModel> users, Context ctx) {
        this.users = users;
        this.ctx = ctx;
        this.inflater=LayoutInflater.from(ctx);
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.item_image,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        UserModel current=users.get(position);
        holder.tvTitle.setText(current.getTitle());

        Bitmap photo=BitmapManager.base64ToBitmap(current.getPhoto());
        Bitmap image=BitmapManager.byteToBitmap(current.getImage());
        holder.ivPhoto.setImageBitmap(image);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivPhoto;
        TextView tvTitle;
        ImageButton btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPhoto=itemView.findViewById(R.id.ivPhoto);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            btnDelete=itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(this);
            ivPhoto.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnDelete: {
                    db= Room.databaseBuilder(ctx, AppDatabase.class, AppConfig.DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                    UserModel user=users.get(getPosition());
                    db.userDao().delete(user);
                    users.remove(getPosition());
                    notifyItemRemoved(getPosition());
                } break;
                case R.id.ivPhoto: {
                    showDetails(users.get(getPosition()),ctx);
                } break;
            }

        }
    }

    private void showDetails(UserModel userModel, Context ctx) {
        BottomSheetDialog dialog=new BottomSheetDialog(ctx);
        dialog.setTitle(userModel.getTitle());
        dialog.setContentView(R.layout.item_details);
        TextView tvTitle=dialog.findViewById(R.id.tvTitle);
        ImageView ivPhoto=dialog.findViewById(R.id.ivPhoto);
        tvTitle.setText(userModel.getTitle());
        ivPhoto.setImageBitmap(BitmapManager.byteToBitmap(userModel.getImage()));
        dialog.show();
    }


}