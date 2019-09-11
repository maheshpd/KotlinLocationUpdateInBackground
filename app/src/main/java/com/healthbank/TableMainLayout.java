package com.healthbank;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.healthbank.classes.VitalHeader;

import java.util.ArrayList;

public class TableMainLayout extends RelativeLayout {
    public final String TAG = "TableMainLayout.java";
    TableLayout tableA;
    TableLayout tableB;
    TableLayout tableC;
    TableLayout tableD;
    TableLayout tableE;
    HorizontalScrollView horizontalScrollViewB;
    HorizontalScrollView horizontalScrollViewD;
    HorizontalScrollView horizontalScrollViewE;
    ScrollView scrollViewC;
    ScrollView scrollViewD;
    Context context;
    int colwidth = 150;
    int padding = 10;
    int[] headerCellsWidth;
    ArrayList<VitalHeader> vitalsdata;
    ArrayList<String> headerdata;

    public TableMainLayout(Context context, ArrayList<VitalHeader> vitalsdata) {
        super(context);
        headerdata = new ArrayList<>();
        headerdata.add("Blood Pressure(diastolic)");
        headerdata.add("Blood Pressure(systolic)");
        headerdata.add("Pulse");
        headerdata.add("Tempurature");
        headerdata.add("Respiration Rate");
        headerdata.add("Weight");
        headerdata.add("Height");

        this.vitalsdata = vitalsdata;
        headerCellsWidth = new int[headerdata.size()];
        this.context = context;
        this.initComponents();
        this.setComponentsId();
        this.setScrollViewAndHorizontalScrollViewTag();
        this.horizontalScrollViewB.addView(this.tableB);
        this.scrollViewC.addView(this.tableC);
        this.scrollViewD.addView(this.horizontalScrollViewD);
        this.horizontalScrollViewD.addView(this.tableD);
        this.horizontalScrollViewE.addView(this.tableE);
        this.addComponentToMainLayout();
        this.setBackgroundColor(context.getResources().getColor(R.color.Whitesmoke));
        this.addTableRowToTableA();

        this.addTableRowToTableB();
        this.tableE.addView(this.componentBTableRowTime());

        this.resizeHeaderHeight();
        this.getTableRowHeaderCellWidth();
        this.generateTableC_AndTable_B();
        this.resizeBodyTableRowHeight();
    }
    // initalized components
    private void initComponents() {
        this.tableA = new TableLayout(this.context);
        this.tableB = new TableLayout(this.context);
        this.tableC = new TableLayout(this.context);
        this.tableD = new TableLayout(this.context);
        this.tableE = new TableLayout(this.context);
        this.horizontalScrollViewB = new MyHorizontalScrollView(this.context);
        this.horizontalScrollViewD = new MyHorizontalScrollView(this.context);
        this.horizontalScrollViewE = new MyHorizontalScrollView(this.context);
        this.scrollViewC = new MyScrollView(this.context);
        this.scrollViewD = new MyScrollView(this.context);

       /* this.horizontalScrollViewB.setScrollBarSize(2);
        this.horizontalScrollViewD.setScrollBarSize(2);
        this.scrollViewC.setScrollBarSize(2);
        this.scrollViewD.setScrollBarSize(2);*/

        this.tableA.setBackgroundColor(Color.GREEN);
        this.horizontalScrollViewB.setBackgroundColor(Color.LTGRAY);
        this.horizontalScrollViewE.setBackgroundColor(Color.LTGRAY);

    }

    private void setComponentsId() {
        this.tableA.setId(1);
        this.horizontalScrollViewB.setId(2);
        this.scrollViewC.setId(3);
        this.scrollViewD.setId(4);
        this.horizontalScrollViewE.setId(5);
    }

    // set tags for some horizontal and vertical scroll view
    private void setScrollViewAndHorizontalScrollViewTag() {
        this.horizontalScrollViewB.setTag("horizontal scroll view b");
        this.horizontalScrollViewE.setTag("horizontal scroll view e");
        this.horizontalScrollViewD.setTag("horizontal scroll view d");
        this.scrollViewC.setTag("scroll view c");
        this.scrollViewD.setTag("scroll view d");
    }
    // we add the components here in our TableMainLayout
    private void addComponentToMainLayout() {
        LayoutParams componentB_Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        componentB_Params.addRule(RelativeLayout.RIGHT_OF, this.tableA.getId());

        LayoutParams componentE_Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        componentE_Params.addRule(RelativeLayout.RIGHT_OF, this.tableA.getId());
        componentE_Params.addRule(RelativeLayout.BELOW, this.horizontalScrollViewB.getId());

        LayoutParams componentC_Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        componentC_Params.addRule(RelativeLayout.BELOW, this.tableA.getId());

        LayoutParams componentD_Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        componentD_Params.addRule(RelativeLayout.RIGHT_OF, this.scrollViewC.getId());
        componentD_Params.addRule(RelativeLayout.BELOW, this.horizontalScrollViewE.getId());
        this.addView(this.tableA);
        this.addView(this.horizontalScrollViewB, componentB_Params);
        this.addView(this.horizontalScrollViewE, componentE_Params);
        this.addView(this.scrollViewC, componentC_Params);
        this.addView(this.scrollViewD, componentD_Params);
    }

    private void addTableRowToTableA() {
        this.tableA.addView(this.componentATableRow());
        this.tableA.addView(this.componentATableRow());
    }
    private void addTableRowToTableB() {
        this.tableB.addView(this.componentBTableRow());
    }
    // generate table row of table A
    TableRow componentATableRow() {
        TableRow componentATableRow = new TableRow(this.context);
        final TextView textView = this.headerTextView("0/0 ");
        textView.setWidth(colwidth);
        TableRow.LayoutParams params = new TableRow.LayoutParams(colwidth, LayoutParams.MATCH_PARENT);
        params.setMargins(1, 1, 1, 1);
        textView.setLayoutParams(params);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click " + textView.getText().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, GraphActivity.class);
                context.startActivity(intent);
            }
        });
        componentATableRow.addView(textView);
        return componentATableRow;
    }
    //for header horizontal
    TableRow componentBTableRow() {
        TableRow componentBTableRow = new TableRow(this.context);
        String key;
        for (int i = 0; i < vitalsdata.size(); i++) {
            TableRow.LayoutParams params = new TableRow.LayoutParams((colwidth*vitalsdata.get(i).getMdataset().size()+vitalsdata.get(i).getMdataset().size()), LayoutParams.MATCH_PARENT);
            params.setMargins(1, 1, 1, 1);
            key = vitalsdata.get(i).getTitle();
            TextView textView = this.headerTextView(key);
            textView.setLayoutParams(params);
            textView.setPadding(padding, padding, padding, padding);
            componentBTableRow.addView(textView);
        }
        return componentBTableRow;
    }

    TableRow componentBTableRowTime(){
        TableRow componentBTableRow = new TableRow(this.context);
        String key;
        for (int i = 0; i < vitalsdata.size(); i++) {
            for(int j=0;j<vitalsdata.get(i).getMdataset().size();j++) {
                TableRow.LayoutParams params = new TableRow.LayoutParams(colwidth, LayoutParams.MATCH_PARENT);
                params.setMargins(1, 1, 1, 1);
                key = vitalsdata.get(i).getMdataset().get(j).getTime();
                TextView textView = this.headerTextView(key);
                textView.setLayoutParams(params);
                textView.setPadding(padding, padding, padding, padding);
                componentBTableRow.addView(textView);
            }
        }
        return componentBTableRow;
    }

    private void generateTableC_AndTable_B() {
        for (int i = 0; i < headerdata.size(); i++) {
            try {
                TableRow taleRowForTableD = this.taleRowForTableD(i);
                taleRowForTableD.setBackgroundColor(Color.LTGRAY);
                this.tableD.addView(taleRowForTableD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < headerdata.size(); i++) {
            try {
                TableRow tableRowForTableC = this.tableRowForTableC(headerdata.get(i));
                tableRowForTableC.setBackgroundColor(Color.LTGRAY);
                this.tableC.addView(tableRowForTableC);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    TableRow tableRowForTableC(String key) {
        TableRow tableRowForTableC = new TableRow(this.context);
        try {
            TableRow.LayoutParams params = new TableRow.LayoutParams(colwidth, LayoutParams.MATCH_PARENT);
            params.setMargins(1, 1, 1, 1);
            final TextView textView = this.bodyTextView(key);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, textView.getText().toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, GraphActivity.class);
                    context.startActivity(intent);
                }
            });
            tableRowForTableC.addView(textView, params);
        } catch (Exception e) {
            //Log.e("error ", "error " + e.toString());
        }
        return tableRowForTableC;
    }

    TableRow taleRowForTableD(int pos) {
        TableRow taleRowForTableD = new TableRow(this.context);
        for (int x = 0; x < vitalsdata.size(); x++) {
            for(int j=0;j<vitalsdata.get(x).getMdataset().size();j++) {
                TableRow.LayoutParams params = new TableRow.LayoutParams(colwidth, LayoutParams.MATCH_PARENT);
                params.setMargins(1, 1, 1, 1);
                TextView textViewB = this.bodyTextView("");
                textViewB.setLayoutParams(params);
                switch (pos) {
                    case 0:
                        textViewB.setText(vitalsdata.get(x).getMdataset().get(j).getHighbloodpressure());
                        break;
                    case 1:
                        textViewB.setText(vitalsdata.get(x).getMdataset().get(j).getLowbloodpressure());
                        break;
                    case 2:
                        textViewB.setText(vitalsdata.get(x).getMdataset().get(j).getPulse());
                        break;
                    case 3:
                        textViewB.setText(vitalsdata.get(x).getMdataset().get(j).getTempurature());
                        break;
                    case 4:
                        textViewB.setText(vitalsdata.get(x).getMdataset().get(j).getRespirationrate());
                        break;
                    case 5:
                        textViewB.setText(vitalsdata.get(x).getMdataset().get(j).getWeight());
                        break;
                    case 6:
                        textViewB.setText(vitalsdata.get(x).getMdataset().get(j).getHeight());
                        break;
                }
                taleRowForTableD.addView(textViewB, params);
            }
        }
        return taleRowForTableD;
    }

    // table cell standard TextView
    TextView bodyTextView(String label) {
        final TextView bodyTextView = new TextView(this.context);
        TableRow.LayoutParams params = new TableRow.LayoutParams(colwidth, LayoutParams.MATCH_PARENT);
        params.setMargins(1, 1, 1, 1);
        bodyTextView.setWidth(colwidth);
        bodyTextView.setLayoutParams(params);
        bodyTextView.setBackgroundColor(Color.WHITE);
        bodyTextView.setText(label);
        bodyTextView.setGravity(Gravity.CENTER);
        bodyTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "" + bodyTextView.getText().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, GraphActivity.class);
                context.startActivity(intent);
            }
        });
        bodyTextView.setPadding(padding, padding, padding, padding);
        return bodyTextView;
    }
    // header standard TextView
    TextView headerTextView(String label) {
        TextView headerTextView = new TextView(this.context);
        headerTextView.setWidth(colwidth);
        TableRow.LayoutParams params = new TableRow.LayoutParams(colwidth, LayoutParams.MATCH_PARENT);
        params.setMargins(1, 1, 1, 1);
        headerTextView.setLayoutParams(params);
        headerTextView.setBackgroundColor(Color.WHITE);
        headerTextView.setText(label);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setPadding(padding, padding, padding, padding);
        return headerTextView;
    }

    // resizing TableRow height starts here
    void resizeHeaderHeight() {
        TableRow productNameHeaderTableRow = (TableRow) this.tableA.getChildAt(0);
        TableRow productInfoTableRow = (TableRow) this.tableB.getChildAt(0);
        int rowAHeight = this.viewHeight(productNameHeaderTableRow);
        int rowBHeight = this.viewHeight(productInfoTableRow);
        TableRow tableRow = rowAHeight < rowBHeight ? productNameHeaderTableRow : productInfoTableRow;
        int finalHeight = rowAHeight > rowBHeight ? rowAHeight : rowBHeight;
        this.matchLayoutHeight(tableRow, finalHeight);
    }

    void getTableRowHeaderCellWidth() {
        try {
            int tableAChildCount = ((TableRow) this.tableA.getChildAt(0)).getChildCount();
            int tableBChildCount = ((TableRow) this.tableB.getChildAt(0)).getChildCount();
            //  Log.e("data ", "data " + tableAChildCount + " " + tableBChildCount);
           /* for (int x = 0; x < (tableAChildCount + tableBChildCount); x++) {
                try {
                    if (x == 0) {
                        this.headerCellsWidth[x] = this.viewWidth(((TableRow) this.tableA.getChildAt(0)).getChildAt(x));
                    } else {
                        this.headerCellsWidth[x] = this.viewWidth(((TableRow) this.tableB.getChildAt(0)).getChildAt(x - 1));
                    }
                } catch (Exception e) {
                    Log.e("error2 ", "error " + e.toString());
                    e.printStackTrace();
                }
            }*/
        } catch (Exception e) {
            Log.e("error ", "error " + e.toString());
            e.printStackTrace();
        }
    }

    // resize body table row height
    void resizeBodyTableRowHeight() {
        int tableC_ChildCount = this.tableC.getChildCount();
        for (int x = 0; x < tableC_ChildCount; x++) {
            TableRow productNameHeaderTableRow = (TableRow) this.tableC.getChildAt(x);
            TableRow productInfoTableRow = (TableRow) this.tableD.getChildAt(x);
            int rowAHeight = this.viewHeight(productNameHeaderTableRow);
            int rowBHeight = this.viewHeight(productInfoTableRow);
            TableRow tableRow = rowAHeight < rowBHeight ? productNameHeaderTableRow : productInfoTableRow;
            int finalHeight = rowAHeight > rowBHeight ? rowAHeight : rowBHeight;
            this.matchLayoutHeight(tableRow, finalHeight);
        }
    }

    // match all height in a table row
    // to make a standard TableRow height
    private void matchLayoutHeight(TableRow tableRow, int height) {

        int tableRowChildCount = tableRow.getChildCount();
        // if a TableRow has only 1 child
        if (tableRow.getChildCount() == 1) {
            View view = tableRow.getChildAt(0);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            params.height = height - (params.bottomMargin + params.topMargin);
            return;
        }
        // if a TableRow has more than 1 child
        for (int x = 0; x < tableRowChildCount; x++) {
            View view = tableRow.getChildAt(x);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            if (!isTheHeighestLayout(tableRow, x)) {
                params.height = height - (params.bottomMargin + params.topMargin);
                return;
            }
        }
    }

    // check if the view has the highest height in a TableRow
    private boolean isTheHeighestLayout(TableRow tableRow, int layoutPosition) {

        int tableRowChildCount = tableRow.getChildCount();
        int heighestViewPosition = -1;
        int viewHeight = 0;

        for (int x = 0; x < tableRowChildCount; x++) {
            View view = tableRow.getChildAt(x);
            int height = this.viewHeight(view);

            if (viewHeight < height) {
                heighestViewPosition = x;
                viewHeight = height;
            }
        }

        return heighestViewPosition == layoutPosition;
    }

    // read a view's height
    private int viewHeight(View view) {
        if (view != null) {
            view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            return view.getMeasuredHeight();
        } else {
            return 100;
        }
    }

    // read a view's width
    private int viewWidth(View view) {
        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    // horizontal scroll view custom class
    class MyHorizontalScrollView extends HorizontalScrollView {
        public MyHorizontalScrollView(Context context) {
            super(context);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) this.getTag();
            if (tag.equalsIgnoreCase("horizontal scroll view b")) {
                horizontalScrollViewD.scrollTo(l, 0);
            } else {
                horizontalScrollViewB.scrollTo(l, 0);
                horizontalScrollViewE.scrollTo(l, 0);
            }
        }

    }

    // scroll view custom class
    class MyScrollView extends ScrollView {

        public MyScrollView(Context context) {
            super(context);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) this.getTag();
            if (tag.equalsIgnoreCase("scroll view c")) {
                scrollViewD.scrollTo(0, t);
            } else {
                scrollViewC.scrollTo(0, t);
            }
        }
    }

}
