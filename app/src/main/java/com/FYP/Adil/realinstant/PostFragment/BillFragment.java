package com.FYP.Adil.realinstant.PostFragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.FYP.Adil.realinstant.Contracts.CardDetailContract;
import com.FYP.Adil.realinstant.Contracts.LastPostIdContract;
import com.FYP.Adil.realinstant.HomeActivity;
import com.FYP.Adil.realinstant.Models.Helper;
import com.FYP.Adil.realinstant.Presenters.CardDetailPresenter;
import com.FYP.Adil.realinstant.Presenters.PostDetailPresenter;
import com.FYP.Adil.realinstant.Presenters.PostPresenter;
import com.FYP.Adil.realinstant.R;
import com.manojbhadane.PaymentCardView;

import java.io.File;

public class BillFragment extends Fragment implements CardDetailContract,LastPostIdContract {
    // TODO: Rename parameter arguments, choose names that match

    private String RadiusBill;
    private String HoursBill;
    private int TotalBill;
    private TextView tvRadiusBill,tvHoursBill,tvTotalBill;
    private Button Next,Previous,CardDetails;
    private PostDetailPresenter postDetailPresenter;
    private PostPresenter postPresenter;
    private CardDetailPresenter cardDetailPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_bill, container, false);


        //make card details preseter
        cardDetailPresenter = new CardDetailPresenter(this);

        //get credit card status network call
        cardDetailPresenter.UserCardStatus(Helper.userId);

        //Make Post Preseter Object
        postPresenter = new PostPresenter(this);

        //set views
        tvHoursBill = view.findViewById(R.id.HoursBill);
        tvRadiusBill = view.findViewById(R.id.RadiusBill);
        tvTotalBill = view.findViewById(R.id.TotalBill);
        CardDetails = view.findViewById(R.id.UserCardDetails);

        CardDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardOpenDialog();
            }
        });

        Next = view.findViewById(R.id.Next);
        Previous = view.findViewById(R.id.Previous);


        Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment PF = new CetagoryFragment();
                PF.setArguments(getArguments());
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container,PF);
                fragmentTransaction.commit();

            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Log.i("BillTag","  this is post frag");
                    // Get file in Bundle and pass into UploadFile Method
                if(getArguments().getString("typeChecker").equals("file")){
                    File file = (File) getArguments().getSerializable("file");
                    // This call for upload IMAGE file
                    postPresenter.uploadFile(file,getArguments().getString("KM"),getArguments().getString("Description"),getArguments().getString("lati")
                            ,getArguments().getString("longi"),getArguments().getBoolean("IsGlobal")
                            ,getArguments().getString("Hours"),getArguments().getLong("CategoryId")+"",Helper.userId+"",true,"image");
                }else{
                    Log.i("check catch","hello catch");
                    postPresenter.uploadText(getArguments().getString("KM"),getArguments().getString("Description"),getArguments().getString("lati")
                            ,getArguments().getString("longi"),getArguments().getBoolean("IsGlobal")
                            ,getArguments().getString("Hours"),getArguments().getLong("CategoryId")+"",Helper.userId+"",true);
                }


                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    getActivity().startActivity(intent);
            }
        });

        RadiusBill = getArguments().getString("RadiusRs");
        HoursBill = getArguments().getString("HoursRs");


        BillSetter();

        return view;
    }


    void BillSetter(){

        double temp1,temp2;

        if(RadiusBill.equals("")){

            temp1 =  Double.parseDouble(HoursBill.substring(3));
            TotalBill = (int) temp1;
           // TotalBill =  Integer.parseInt(HoursBill.substring(3));

            tvRadiusBill.setText("Radius Bill: 0");
            tvHoursBill.setText("HoursBill: "+HoursBill);
            tvTotalBill.setText("Total Bill: "+TotalBill+" €");
        }else if(HoursBill.equals("")){

            temp2 = Double.parseDouble(RadiusBill.substring(2));

            TotalBill = (int) temp2;
            //  TotalBill = Integer.parseInt(RadiusBill.substring(2));

            tvRadiusBill.setText("Radius Bill: "+RadiusBill);
            tvHoursBill.setText("HoursBill: 0");
            tvTotalBill.setText("Total Bill: "+TotalBill+" € ");
        }else{

            temp1 =  Double.parseDouble(HoursBill.substring(3));
            temp2 = Double.parseDouble(RadiusBill.substring(2));
            TotalBill = (int) temp1 + (int) temp2;

          //  TotalBill = Integer.parseInt(RadiusBill.substring(2)) + Integer.parseInt(HoursBill.substring(2));

            tvRadiusBill.setText("Radius Bill: "+RadiusBill);
            tvHoursBill.setText("HoursBill: "+HoursBill);
            tvTotalBill.setText("Total Bill: "+TotalBill+" € ");
        }
    }


    //this function is use to opoup dialog box for Enter Card Details against the post
    public void CardOpenDialog(){
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View subView = inflater.inflate(R.layout.credit_card_view, null);
        final PaymentCardView paymentCard =  subView.findViewById(R.id.creditCard);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();


        paymentCard.setOnPaymentCardEventListener(new PaymentCardView.OnPaymentCardEventListener() {
            @Override
            public void onCardDetailsSubmit(String month, String year, String cardNumber, String cvv) {
//                Log.i("Card Details",year+" "+cardNumber+" "+cvv);

                CardDetailCall(cardNumber+"",cvv+"",month+"/"+year);
                paymentCard.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Card Details Entered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelClick() {
                subView.setVisibility(subView.GONE);
            }
        });
        builder.show();
    }
    @Override
    public void CardDetailCall(String CardNo, String CVV, String ExpDate) {

        Log.i("Card Details",CardNo+" "+CVV+" "+ExpDate);
        CardNo = CardNo.replace(" " , "");

        cardDetailPresenter.CardDetailCall(CardNo,CVV,ExpDate,Helper.userId+"");

        CardDetails.setText("Already Card Details Entered");
        CardDetails.setEnabled(false);

       // cardDetailPresenter.CardDetailCall("7393 9202 8373 929",CVV,ExpDate,Helper.userId+"");
    }
    @Override
    public void getUserCardStatus(String status) {
        if(status.equals("1")){
            CardDetails.setText("Already Card Details Entered");
            CardDetails.setEnabled(false);
        }

    }

    @Override
    public void LastPostId(String lastPostId) {
        postDetailPresenter = new PostDetailPresenter();
        Log.i("check", HoursBill +"  "+RadiusBill);
        postDetailPresenter.LoadDetails(HoursBill+"",RadiusBill+"",TotalBill+"",lastPostId+"",Helper.userId+"");
    }

}
