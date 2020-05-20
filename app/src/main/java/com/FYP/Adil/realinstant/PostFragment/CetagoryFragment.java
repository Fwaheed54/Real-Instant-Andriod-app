package com.FYP.Adil.realinstant.PostFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.FYP.Adil.realinstant.Contracts.CategoryContract;
import com.FYP.Adil.realinstant.Models.Category;
import com.FYP.Adil.realinstant.OtherObjects.LocalCoordinate;
import com.FYP.Adil.realinstant.OtherObjects.LocationHelper;
import com.FYP.Adil.realinstant.Presenters.CategoryPresenter;
import com.FYP.Adil.realinstant.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


public class CetagoryFragment extends Fragment implements CategoryContract{
    // TODO: Rename parameter arguments, choose names that match

    //All private declatraions for this Fragmengts
    Spinner spinner;
    private EditText Description;
    private Button Next;
    private Bundle bundle = new Bundle();
    private long CategoryID;
    private Switch TypeOfPost;
    private boolean isCheck = false,isSpinner = false;
    private  String lati,longi;
    private LocationHelper locationHelper;
    private CategoryPresenter categoryPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_cetagory,container,false);

        //make category presenter object
        categoryPresenter = new CategoryPresenter(this);

        //call get all category function
        categoryPresenter.loadCategory();

        //This for again new call for location helper class for getting current cordinates
        locationHelper = new LocationHelper(getContext(), (AppCompatActivity) getActivity());
        locationHelper.locationSetup();

        // Make objects
        spinner = view.findViewById(R.id.Category);
        Description = view.findViewById(R.id.Discription);
        TypeOfPost = view.findViewById(R.id.switch1);

        //this line of code is use to check if user back from the next (parameter fragment then fill all attributes
        try {
            getArguments().getString("lati");
            PreviousSetup(getArguments());
        }catch (Exception e){
            e.getMessage();
        }

        TypeOfPost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               TextView TypeOfText = view.findViewById(R.id.SwitchText);
               if(isChecked) {
                   TypeOfText.setText("This For Local Post");
                   isCheck = isChecked;
               }
               else{
                   TypeOfText.setText("This For Global Post");
                   isCheck = isChecked;
               }
            }
        });

        Next = view.findViewById(R.id.Next);


        Next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                // This If statement check description is null or not is its null then it put in it "No Description" string
                String tempDes;
                if(Description.getText().toString().equals(""))
                    tempDes = "No Description";
                else
                    tempDes = Description.getText().toString();

                //Make bundle to send all fragement data to an other fragement and apply if else for checking text and image post
                if(getArguments() != null){
                    bundle = getArguments();
                    bundle.putString("typeChecker","file");
                    bundle.putString("Description",tempDes);
                    bundle.putLong("CategoryId",CategoryID);
                    bundle.putBoolean("IsGlobal",isCheck);
                    bundle.putString("lati",lati);
                    bundle.putString("longi",longi);

                }else{
                    bundle.putString("typeChecker", "Text");
                    bundle.putString("Description",tempDes);
                    bundle.putLong("CategoryId",CategoryID);
                    bundle.putBoolean("IsGlobal",isCheck);
                    bundle.putString("lati",lati);
                    bundle.putString("longi",longi);
                }


                //Make new fregements object withe type of Category fregements
                Fragment PF =  new ParameterFragment();
                PF.setArguments(bundle);
                //Trensfer Camera fregement into Category Fregemenet
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container,PF);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    //previous button setting
    public void PreviousSetup(Bundle bundle){
        this.bundle = bundle;
        Description.setText(bundle.get("Description").toString());
        TypeOfPost.setChecked(bundle.getBoolean("IsGlobal"));
    }
    // This method generate retrofit call and get all categories which are loacted  in database and also set in spinner
    @Override
    public void getCategory(ArrayList<Category> categories) {
        // Make Array list to get Catrgories name in category object
        final ArrayList<String> Names = new ArrayList<>();
        //This Loop set categories in Array list
        for (Category category :
                categories) {
            Names.add(category.CategoryType);
        }
        // Make UI Thread
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Make array adapter and set Category array list in it
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,Names);
                    spinner.setAdapter(adapter);
            }
        });
        //on item select event handling
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryID = id;
                Log.i("Mtag Spinner",position+" position  "+CategoryID+"  id");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner.setSelection((int) bundle.getLong("CategoryId"));
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSimpleEvent(LocalCoordinate localCoordinate) {
        lati = localCoordinate.getLatitude();
        longi = localCoordinate.getLongitude();

        Log.i("category Loc Event Bus",lati+" "+longi);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}
