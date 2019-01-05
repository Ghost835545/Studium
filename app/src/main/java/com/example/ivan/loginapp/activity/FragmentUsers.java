package com.example.ivan.loginapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ivan.loginapp.Group;
import com.example.ivan.loginapp.R;
import com.example.ivan.loginapp.User;
import com.example.ivan.loginapp.rest.Connection;

public class FragmentUsers extends Fragment {


    private static int SCREEN_HEIGHT;
    private static int SCREEN_WIDTH;
    RelativeLayout relativeLayoutMain;


    RelativeLayout relativeLayoutB;

    RelativeLayout relativeLayoutD;

    TableLayout tableLayoutA;
    TableLayout tableLayoutB;
    TableLayout tableLayoutC;
    TableLayout tableLayoutD;

    TableRow tableRow;
    TableRow tableRowB;

    /*HorizontalScroll horizontalScrollViewB;
    HorizontalScroll horizontalScrollViewD;

    VerticalScroll scrollViewC;
    VerticalScroll scrollViewD;
    */

    /*
        This is for counting how many columns are added in the row.
   */
    int tableColumnCountB = 0;

    /*
         This is for counting how many row is added.
    */
    int tableRowCountC = 0;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        return inflater.inflate(R.layout.fragment_users, container, false);

    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Список пользователей");

        relativeLayoutMain = (RelativeLayout) getActivity().findViewById(R.id.relativeLayoutMain);
        getScreenDimension();
        initializeRelativeLayout();
        initializeScrollers();
        initializeTableLayout();
        /*horizontalScrollViewB.setScrollViewListener(this);
        horizontalScrollViewD.setScrollViewListener(this);
        //scrollViewC.setScrollViewListener(this);
        scrollViewD.setScrollViewListener(this);
        //addRowToTableA();
        */
        initializeRowForTableB();

        /*
            Till Here.
         */


        /*  There is two unused functions
            Have a look on these functions and try to recreate and use it.
            createCompleteColumn();
            createCompleteRow();
        */
        User user = new User();
        HttpRequestTask task = new HttpRequestTask(user);
        task.execute();

    }

    private class HttpRequestTask extends AsyncTask<Void, Void, User[]> {
        User user = null;

        HttpRequestTask(User newuser) {
            this.user = newuser;
        }

        @Override
        protected User[] doInBackground(Void... params) {
            try {

                User[] users = new Connection().getUsers();
                return users;


            } catch (Exception e) {

            }
            return new User[0];
        }

        @Override
        protected void onPostExecute(User[] users) {
            for (int i = 0; i < 4; i++) {
                addColumnsToTableB("ID", i);
                addColumnsToTableB("FIO", i);
                addColumnsToTableB("Phone", i);
                addColumnsToTableB("email", i);
                addColumnsToTableB("Raiting", i);
            }
            for (int i = 0; i < users.length; i++) {
                initializeRowForTableD(i);
                //addRowToTableC("Row"+ i);
                //for(int j=0; j<tableColumnCountB; j++){
                addColumnToTableAtD(i, users[i].getIdUser().toString());
                addColumnToTableAtD(i, users[i].getFio().toString());
                addColumnToTableAtD(i, users[i].getPhone().toString());
                addColumnToTableAtD(i, users[i].getEmail().toString());

                //}
            }
        }
    }

    private void getScreenDimension() {
        WindowManager wm = (WindowManager) getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SCREEN_WIDTH = size.x;
        SCREEN_HEIGHT = size.y;
    }

    private void initializeRelativeLayout() {
        //relativeLayoutA= new RelativeLayout(getApplicationContext());
        //relativeLayoutA.setId(R.id.relativeLayoutA);
        //relativeLayoutA.setPadding(0,0,0,0);

        relativeLayoutB = new RelativeLayout(getActivity().getApplicationContext());
        relativeLayoutB.setId(R.id.relativeLayoutB);
        relativeLayoutB.setPadding(0, 0, 0, 0);

        /*relativeLayoutC= new RelativeLayout(getApplicationContext());
        relativeLayoutC.setId(R.id.relativeLayoutC);
        relativeLayoutC.setPadding(0,0,0,0);*/

        relativeLayoutD = new RelativeLayout(getActivity().getApplicationContext());
        relativeLayoutD.setId(R.id.relativeLayoutD);
        relativeLayoutD.setPadding(0, 0, 0, 0);

        //relativeLayoutA.setLayoutParams(new RelativeLayout.LayoutParams(SCREEN_WIDTH/5,SCREEN_HEIGHT/20));
        //this.relativeLayoutMain.addView(relativeLayoutA);


        RelativeLayout.LayoutParams layoutParamsRelativeLayoutB = new RelativeLayout.LayoutParams(SCREEN_WIDTH - (SCREEN_WIDTH / 5), SCREEN_HEIGHT / 20);
        layoutParamsRelativeLayoutB.addRule(RelativeLayout.ALIGN_LEFT);
        relativeLayoutB.setLayoutParams(layoutParamsRelativeLayoutB);
        this.relativeLayoutMain.addView(relativeLayoutB);

        /*RelativeLayout.LayoutParams layoutParamsRelativeLayoutC= new RelativeLayout.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT - (SCREEN_HEIGHT/20));
        layoutParamsRelativeLayoutC.addRule(RelativeLayout.BELOW, R.id.relativeLayoutA);
        relativeLayoutC.setLayoutParams(layoutParamsRelativeLayoutC);
        this.relativeLayoutMain.addView(relativeLayoutC);*/

        RelativeLayout.LayoutParams layoutParamsRelativeLayoutD = new RelativeLayout.LayoutParams(SCREEN_WIDTH - (SCREEN_WIDTH / 5), SCREEN_HEIGHT - (SCREEN_HEIGHT / 20));
        layoutParamsRelativeLayoutD.addRule(RelativeLayout.BELOW, R.id.relativeLayoutB);
        layoutParamsRelativeLayoutD.addRule(RelativeLayout.RIGHT_OF, R.id.relativeLayoutC);
        relativeLayoutD.setLayoutParams(layoutParamsRelativeLayoutD);
        this.relativeLayoutMain.addView(relativeLayoutD);

    }

    private void initializeScrollers() {
        /*horizontalScrollViewB= new HorizontalScroll(getApplicationContext());
        horizontalScrollViewB.setPadding(0,0,0,0);

        horizontalScrollViewD= new HorizontalScroll(getApplicationContext());
        horizontalScrollViewD.setPadding(0,0,0,0);

        //scrollViewC= new VerticalScroll(getApplicationContext());
        //scrollViewC.setPadding(0,0,0,0);

        scrollViewD= new VerticalScroll(getApplicationContext());
        scrollViewD.setPadding(0,0,0,0);

        horizontalScrollViewB.setLayoutParams(new ViewGroup.LayoutParams(SCREEN_WIDTH- (SCREEN_WIDTH/5), SCREEN_HEIGHT/20));
        //scrollViewC.setLayoutParams(new ViewGroup.LayoutParams(SCREEN_WIDTH/5 ,SCREEN_HEIGHT - (SCREEN_HEIGHT/20)));
        scrollViewD.setLayoutParams(new ViewGroup.LayoutParams(SCREEN_WIDTH- (SCREEN_WIDTH/5), SCREEN_HEIGHT - (SCREEN_HEIGHT/20) ));
        horizontalScrollViewD.setLayoutParams(new ViewGroup.LayoutParams(SCREEN_WIDTH- (SCREEN_WIDTH/5), SCREEN_HEIGHT - (SCREEN_HEIGHT/20) ));

        this.relativeLayoutB.addView(horizontalScrollViewB);
        // this.relativeLayoutC.addView(scrollViewC);
        this.scrollViewD.addView(horizontalScrollViewD);
        this.relativeLayoutD.addView(scrollViewD);*/

    }

    private void initializeTableLayout() {
        //tableLayoutA= new TableLayout(getApplicationContext());
        //tableLayoutA.setPadding(0,0,0,0);

        tableLayoutB = new TableLayout(getActivity().getApplicationContext());
        tableLayoutB.setPadding(0, 0, 0, 0);
        //tableLayoutB.setId(R.id.tableLayoutB);
        //tableLayoutC= new TableLayout(getApplicationContext());
        //tableLayoutC.setPadding(0,0,0,0);
        tableLayoutD = new TableLayout(getActivity().getApplicationContext());
        tableLayoutD.setPadding(0, 0, 0, 0);

        //TableLayout.LayoutParams layoutParamsTableLayoutA= new TableLayout.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        //tableLayoutA.setLayoutParams(layoutParamsTableLayoutA);
        //tableLayoutA.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        //this.relativeLayoutA.addView(tableLayoutA);


        TableLayout.LayoutParams layoutParamsTableLayoutB = new TableLayout.LayoutParams(SCREEN_WIDTH - (SCREEN_WIDTH / 5), SCREEN_HEIGHT / 20);
        tableLayoutB.setLayoutParams(layoutParamsTableLayoutB);
        tableLayoutB.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));
        this.relativeLayoutB.addView(tableLayoutB);

        /*TableLayout.LayoutParams layoutParamsTableLayoutC= new TableLayout.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT - (SCREEN_HEIGHT/20));
        tableLayoutC.setLayoutParams(layoutParamsTableLayoutC);
        tableLayoutC.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
        this.scrollViewC.addView(tableLayoutC);*/

        TableLayout.LayoutParams layoutParamsTableLayoutD = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        tableLayoutD.setLayoutParams(layoutParamsTableLayoutD);
        this.relativeLayoutD.addView(tableLayoutD);

    }

    /*@Override
    public void onScrollChanged(HorizontalScroll scrollView, int x, int y, int oldx, int oldy) {
        if(scrollView == horizontalScrollViewB){
            horizontalScrollViewD.scrollTo(x,y);
        }
        else if(scrollView == horizontalScrollViewD){
            horizontalScrollViewB.scrollTo(x, y);
        }

    }

    @Override
    public void onScrollChanged(VerticalScroll scrollView, int x, int y, int oldx, int oldy) {
        if(scrollView == scrollViewC){
            scrollViewD.scrollTo(x,y);
        }
        else if(scrollView == scrollViewD){
            scrollViewC.scrollTo(x,y);
        }
    }*/

    private void addRowToTableA() {
        tableRow = new TableRow(getActivity().getApplicationContext());
        TableRow.LayoutParams layoutParamsTableRow = new TableRow.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20);
        tableRow.setLayoutParams(layoutParamsTableRow);
        TextView label_date = new TextView(getActivity().getApplicationContext());
        //label_date.setText("Item/ID");
        tableRow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        tableRow.addView(label_date);
        this.tableLayoutA.addView(tableRow);

    }

    private void initializeRowForTableB() {
        tableRowB = new TableRow(getActivity().getApplicationContext());
        TableRow.LayoutParams layoutParamsTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, SCREEN_HEIGHT / 20);
        tableRowB.setPadding(0, 0, 0, 0);
        this.tableLayoutB.addView(tableRowB);


    }

    private synchronized void addColumnsToTableB(String text, final int id) {
        tableRow = new TableRow(getActivity().getApplicationContext());
        TableRow.LayoutParams layoutParamsTableRow = new TableRow.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20);
        tableRow.setPadding(3, 3, 3, 4);
        tableRow.setLayoutParams(layoutParamsTableRow);
        TextView label_date = new TextView(getActivity().getApplicationContext());
        label_date.setText(text);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        this.tableRow.addView(label_date);
        this.tableRow.setTag(id);
        this.tableRowB.addView(tableRow);
        tableColumnCountB++;
    }

    /*private synchronized void addRowToTableC(String text){
        TableRow tableRow1= new TableRow(getApplicationContext());
        TableRow.LayoutParams layoutParamsTableRow1= new TableRow.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        tableRow1.setPadding(3,3,3,4);
        tableRow1.setLayoutParams(layoutParamsTableRow1);
        TextView label_date = new TextView(getApplicationContext());
        label_date.setText(text);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        tableRow1.addView(label_date);

        TableRow tableRow= new TableRow(getApplicationContext());
        TableRow.LayoutParams layoutParamsTableRow= new TableRow.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        tableRow.setPadding(0,0,0,0);
        tableRow.setLayoutParams(layoutParamsTableRow);
        tableRow.addView(tableRow1);
        this.tableLayoutC.addView(tableRow, tableRowCountC);
        tableRowCountC++;
    }*/

    private synchronized void initializeRowForTableD(int pos) {
        TableRow tableRowB = new TableRow(getActivity().getApplicationContext());
        TableRow.LayoutParams layoutParamsTableRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, SCREEN_HEIGHT / 20);
        tableRowB.setPadding(0, 0, 0, 0);
        tableRowB.setLayoutParams(layoutParamsTableRow);
        this.tableLayoutD.addView(tableRowB, pos);
    }

    @SuppressLint("ResourceAsColor")
    private synchronized void addColumnToTableAtD(final int rowPos, String text) {
        TableRow tableRowAdd = (TableRow) this.tableLayoutD.getChildAt(rowPos);
        tableRow = new TableRow(getActivity().getApplicationContext());
        TableRow.LayoutParams layoutParamsTableRow = new TableRow.LayoutParams(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 20);
        tableRow.setPadding(3, 3, 3, 4);
        tableRow.setBackground(getResources().getDrawable(R.drawable.cell_bacground));
        tableRow.setLayoutParams(layoutParamsTableRow);
        TextView label_date = new TextView(getActivity().getApplicationContext());
        label_date.setText(text);
        label_date.setTextColor(R.color.design_default_color_primary_dark);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        tableRow.setTag(label_date);
        this.tableRow.addView(label_date);
        tableRowAdd.addView(tableRow);
    }

    private void createCompleteColumn(String value) {
        int i = 0;
        int j = tableRowCountC - 1;
        for (int k = i; k <= j; k++) {
            addColumnToTableAtD(k, value);
        }
    }

    private void createCompleteRow(String value) {
        initializeRowForTableD(0);
        int i = 0;
        int j = tableColumnCountB - 1;
        int pos = tableRowCountC - 1;
        for (int k = i; k <= j; k++) {
            addColumnToTableAtD(pos, value);
        }
    }
}
