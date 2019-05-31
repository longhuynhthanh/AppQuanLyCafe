package com.example.admin.quanlycafe.FRAGMENT;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.admin.quanlycafe.ADAPTER.TableAdapter;
import com.example.admin.quanlycafe.DAO.TableFoodDAO;
import com.example.admin.quanlycafe.INTERFACE.TableFoodInterface;
import com.example.admin.quanlycafe.INTERFACE.UpdateTable;
import com.example.admin.quanlycafe.MODEL.TableFood;
import com.example.admin.quanlycafe.R;

import java.util.List;

public class TableFragment extends Fragment implements UpdateTable {
    private static TableFragment instance;
    private UpdateTable updateTableStatusTable = this;

    public UpdateTable getUpdateTableStatusTable()
    {
        return updateTableStatusTable;
    }
    public static TableFragment Instance()
    {
        if (instance == null)
        {
            instance = new TableFragment();
        }
        return instance;
    }
    private void Instance(TableFragment instance)
    {
        TableFragment.instance = instance;
    }

    private RecyclerView rvTable;
    private FloatingActionButton fab;
    private List<TableFood> ListTableFood;
    private TableFoodInterface TableFoodDAO;
    private TableAdapter tableAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getActivity().setTitle("Danh Sách Bàn Ăn");
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        rvTable = (RecyclerView)view.findViewById(R.id.rv_table);
        rvTable.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        rvTable.setHasFixedSize(true);
        fab = (FloatingActionButton)view.findViewById(R.id.insertTable);
        TableFoodDAO = new TableFoodDAO(this.getContext());
        UpdateInterface();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerDialog = new AlertDialog.Builder(TableFragment.this.getActivity());
                LayoutInflater inf = getLayoutInflater();
                final  View view = inf.inflate(R.layout.dialog_table, null);
                alerDialog.setView(view);
                alerDialog.setNegativeButton("Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_NameTable = (EditText)view.findViewById(R.id.etNameTableDA);
                        TableFood tableFood = new TableFood(et_NameTable.getText().toString());
                        TableFoodDAO.InsertTableFood(tableFood);
                        tableAdapter.addItem(tableFood);
                    }
                });

                alerDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alerDialog.show();
            }
        });

        return view;
    }


    public void UpdateInterface()
    {
        ListTableFood = TableFoodDAO.GetTableFood();
        tableAdapter = new TableAdapter(ListTableFood, this);
        rvTable.setAdapter(tableAdapter);
    }

    @Override
    public void UpdateStatusTable(int idTable, int status) {
        tableAdapter.UpdateTable(idTable, status);
        TableFoodDAO.UpdateTableFood(idTable, status);
        rvTable.setAdapter(tableAdapter);
    }

}
