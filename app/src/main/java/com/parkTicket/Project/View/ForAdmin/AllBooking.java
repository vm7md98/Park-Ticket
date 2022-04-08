package com.parkTicket.Project.View.ForAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.parkTicket.Project.DAO.AdminBooking;
import com.parkTicket.Project.R;
import com.parkTicket.Project.model.ClassBooking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllBooking extends AppCompatActivity {


    private TextView tvData;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_booking);


        Intent intent = getIntent();

        tvData = findViewById(R.id.tvDataBook);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("booking");

        String showall = intent.getStringExtra("showall");
        int showbknum = intent.getIntExtra("findbknum",0);

        if(showall != null)
        {
            displayAll();
        }

        if(showbknum > 0 )
        {
            find(String.valueOf(showbknum));
        }
    }

    public void displayAll()
    {
//        MyDBHelper myhelper = new MyDBHelper(this);
//        bks  = myhelper.getBookings();
//
//        //Toast.makeText(this, mbrs.toString(), Toast.LENGTH_LONG).show();
//
//        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//        // android.R.layout.simple_list_item_1, android.R.id.text1, m);
//
//        ArrayAdapter<String> bookings = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bks);
//
//        listView.setAdapter(bookings);
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                // TODO Auto-generated method stub
//                ClassBooking value= (ClassBooking) bks.get(position);
//
//                // Create an intent an pass the object to the detail screen
//
//                Toast.makeText(getApplicationContext(),value.getBooking_num(),Toast.LENGTH_SHORT).show();
//
//            }
//        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ClassBooking> bookings = new ArrayList<>();
                for(DataSnapshot node:dataSnapshot.getChildren())
                {
                    ClassBooking booking = node.getValue(ClassBooking.class);
                    bookings.add(booking);
                }

                updateUI(bookings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // find data of park
    public void find(String bknum)
    {

//        MyDBHelper myhelper = new MyDBHelper(this);
//        List    b  = myhelper.getBookingByNum(bknum);
//
//        ArrayAdapter<String> bookings = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, b);
//
//        listView.setAdapter(bookings);

        //show member by id
        reference.child(bknum).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClassBooking booking = dataSnapshot.getValue(ClassBooking.class);

                String details =
                                "booking number: " + booking.getBooking_num() + "\n" +
                                "park number: " + booking.getPark_num() + "\n" +
                                "member number: " + booking.getId() + "\n" +
                                "number of ticket: " + booking.getNumber_of_ticket() + "\n" +
                                "total price: " + booking.getTotal_price() + "\n\n" ;

                //tvData.setText(String.valueOf(member.getId()+1));
                tvData.setText(details);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updateUI (List<ClassBooking> bookings)
    {
        String bookingsDetails = "";
        for(ClassBooking booking : bookings)
        {
            bookingsDetails += "booking number: " + booking.getBooking_num() + "\n" +
                    "park number: " + booking.getPark_num() + "\n" +
                    "member number: " + booking.getId() + "\n" +
                    "number of ticket: " + booking.getNumber_of_ticket() + "\n" +
                    "total price: " + booking.getTotal_price() + "\n\n" ;

        }

        tvData.setText(bookingsDetails);
    }

    //back to home
    public void Back(View view)
    {
        Intent intent = new Intent(this, AdminBooking.class);
        startActivity(intent);
    }
}