package com.example.kibutripbooking.Admin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kibutripbooking.R;

import java.util.List;

public class DriverList extends ArrayAdapter<Driver> {

    private final Activity context;
    private final List<Driver> busList;

    public DriverList(Activity context, List<Driver> busList) {
        super(context, R.layout.list_driver, busList);
        this.context = context;
        this.busList = busList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_driver, null, true);


        TextView textViewDriverName = listViewItem.findViewById(R.id.text_view_busName);
        TextView textViewBusNumber = listViewItem.findViewById(R.id.text_view_busNumber);
        TextView textViewDriverPhoneNo=listViewItem.findViewById(R.id.text_view_busFare);
        TextView textViewDriverLicense = listViewItem.findViewById(R.id.text_view_date);
        TextView textViewDriveraddress=listViewItem.findViewById(R.id.text_view_time);
        TextView textViewRegion = listViewItem.findViewById(R.id.text_view_from);
        TextView book = listViewItem.findViewById(R.id.book);


        Driver driver = busList.get(position);

        textViewDriverName.setText(driver.getDriverNameI());
        textViewBusNumber.setText(driver.getBusNumberI());
        textViewDriverPhoneNo.setText(driver.getPhone());
        textViewDriverLicense.setText(driver.getLicense());
        textViewDriveraddress.setText(driver.getAddress());
        textViewRegion.setText(driver.getReg());


        return listViewItem;
    }
}
