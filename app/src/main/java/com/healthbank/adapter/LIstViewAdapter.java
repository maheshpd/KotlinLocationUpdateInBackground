package com.healthbank.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.healthbank.Fonter;
import com.healthbank.R;
import com.healthbank.classes.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class LIstViewAdapter extends BaseExpandableListAdapter implements OnItemClickListener {
    public static final int ItemHeight = 48;
    public static final int PaddingLeft = 36;
    List<TreeNode> treeNodes = new ArrayList<TreeNode>();
    Context parentContext;
    LayoutInflater layoutInflater;
    private int myPaddingLeft = 0;
    private MyGridView toolbarGrid;

    public LIstViewAdapter(Context view, List<TreeNode> treenode) {
        parentContext = view;
        this.treeNodes = treenode;
    }

    public void RemoveAll() {
        treeNodes.clear();
    }

    public Object getChild(int groupPosition, int childPosition) {
        return treeNodes.get(groupPosition).childs.get(childPosition);
    }

    public int getChildrenCount(int groupPosition) {
        //Log.e("child ", "child " + treeNodes.get(groupPosition).childs.size());
        return treeNodes.get(groupPosition).childs.size();
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) parentContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.parent_row, null);
        TextView tv = convertView.findViewById(R.id.item_text1);
        tv.setTypeface(Fonter.getTypefacesemibold(parentContext));
        tv.setText(getGroup(groupPosition).toString());
        return tv;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public Object getGroup(int groupPosition) {
        return treeNodes.get(groupPosition).parent;
    }

    public int getGroupCount() {
        return treeNodes.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }


    @Override
    public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
                             ViewGroup arg4) {
        // TODO Auto-generated method stub
        layoutInflater = (LayoutInflater) parentContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arg3 = layoutInflater.inflate(R.layout.view, null);
        toolbarGrid = arg3.findViewById(R.id.GridView_toolbar);
        toolbarGrid.setNumColumns(3);
        toolbarGrid.setGravity(Gravity.CENTER);
        toolbarGrid.setHorizontalSpacing(10);
        ArrayAdapterTimeslot itemsAdapter = new ArrayAdapterTimeslot(parentContext, treeNodes.get(arg0).subchilds);
        toolbarGrid.setAdapter(itemsAdapter);
        toolbarGrid.setOnItemClickListener(this);
        //}
        return arg3;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }
}