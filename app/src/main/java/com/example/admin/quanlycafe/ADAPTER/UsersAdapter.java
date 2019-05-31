package com.example.admin.quanlycafe.ADAPTER;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.quanlycafe.DAO.AccountDAO;
import com.example.admin.quanlycafe.INTERFACE.ItemClickListener;
import com.example.admin.quanlycafe.MODEL.Account;
import com.example.admin.quanlycafe.R;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
    private List<Account> ListAccount;
    private Fragment fragment;

    public UsersAdapter(List<Account> list, Fragment fragment)
    {
        this.ListAccount = list;
        this.fragment = fragment;
    }
    public void RemoveItem(String s)
    {
        for(int i = 0; i < this.ListAccount.size(); i++)
        {
            if(this.ListAccount.get(i).UserName().equals(s))
            {
                this.ListAccount.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }
    public void addItem(Account a)
    {
        this.ListAccount.add(a);
        notifyItemInserted(this.ListAccount.size() - 1);
    }

    public void UpdateItem(Account a)
    {
        int index = 0;
        for(Account account : this.ListAccount)
        {
            if(account.UserName().equals(a.UserName()))
            {
                this.ListAccount.set(index, a);
                notifyItemChanged(index);
                break;
            }
            index++;
        }
    }

    public void UpdateAll(List<Account> list)
    {
        this.ListAccount.clear();
        this.ListAccount.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int i) {
        final Account account = this.ListAccount.get(i);
        usersViewHolder.tvDisplayName.setText("Tên Nhân Viên: " + account.DisplayName());
        usersViewHolder.tvUserName.setText("User Name: " + account.UserName());
        usersViewHolder.tvPassword.setText("Mật Khẩu: " + account.PasswordAccount());
        String s = "";
        if(account.Type() == 1)
        {
            s = "Quản Lý";
        }
        else if(account.Type() == 0)
        {
            s = "Nhân Viên";
        }
        usersViewHolder.tvType.setText("Chức Vụ: " + s);
        usersViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                {}
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
                    view = fragment.getActivity().getLayoutInflater().inflate(R.layout.dialog_user, null);
                    final EditText etUserName = (EditText)view.findViewById(R.id.etUserNameDA);
                    etUserName.setText(account.UserName());
                    etUserName.setEnabled(false);
                    final EditText etDisplayName = (EditText)view.findViewById(R.id.etDisplayNameDA);
                    etDisplayName.setText(account.DisplayName());
                    final EditText etPassword = (EditText)view.findViewById(R.id.etPasswordDA);
                    etPassword.setText(account.PasswordAccount());

                    builder.setView(view);
                    builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String s = etUserName.getText().toString();
                            AccountDAO accountDAO = new AccountDAO(fragment.getContext());
                            accountDAO.DeleteAccount(s);
                            RemoveItem(s);
                        }
                    });
                    builder.setNegativeButton("UpdateTable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Account updateAccount = new Account(etUserName.getText().toString(),
                                                                etDisplayName.getText().toString(),
                                                                etPassword.getText().toString(),
                                                                0);
                            AccountDAO accountDAO = new AccountDAO(fragment.getContext());
                            accountDAO.UpdateAccount(updateAccount);
                            UpdateItem(updateAccount);
                        }
                    });
                    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.ListAccount.size();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView tvDisplayName;
        private TextView tvUserName;
        private TextView tvPassword;
        private TextView tvType;

        private ItemClickListener itemClickListener;
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDisplayName = (TextView)itemView.findViewById(R.id.tv_displayName);
            tvUserName = (TextView)itemView.findViewById(R.id.tv_userName);
            tvPassword = (TextView)itemView.findViewById(R.id.tv_passWord);
            tvType = (TextView)itemView.findViewById(R.id.tv_type);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.OnClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            this.itemClickListener.OnClick(v, getAdapterPosition(), true);
            return true;
        }
    }
}
