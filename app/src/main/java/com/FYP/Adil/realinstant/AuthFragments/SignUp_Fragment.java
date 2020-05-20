package com.FYP.Adil.realinstant.AuthFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.FYP.Adil.realinstant.Contracts.AuthContract;
import com.FYP.Adil.realinstant.LoginActivity;
import com.FYP.Adil.realinstant.Models.AuthMassage;
import com.FYP.Adil.realinstant.Models.SignupUser;
import com.FYP.Adil.realinstant.Models.UserLogin;
import com.FYP.Adil.realinstant.OtherObjects.CustomToast;
import com.FYP.Adil.realinstant.OtherObjects.Utils;
import com.FYP.Adil.realinstant.Presenters.AuthPresenter;
import com.FYP.Adil.realinstant.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp_Fragment extends Fragment implements OnClickListener , AuthContract {
	private static View view;
	private static EditText fullName,lastName, emailId, mobileNumber, location,
			password, confirmPassword;
	private static TextView login;
	private static Button signUpButton;
	private static CheckBox terms_conditions;
	private AuthPresenter authPresenter;

	public SignUp_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.signup_layout, container, false);

		initViews();
		setListeners();

		authPresenter = new AuthPresenter(this);

		return view;
	}

	// Initialize all views
	private void initViews() {
		fullName = (EditText) view.findViewById(R.id.fullName);
		lastName = view.findViewById(R.id.LastNameId);
		emailId = (EditText) view.findViewById(R.id.userEmailId);
		mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
		location = (EditText) view.findViewById(R.id.location);
		password = (EditText) view.findViewById(R.id.password);
		confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
		signUpButton = (Button) view.findViewById(R.id.signUpBtn);
		login = (TextView) view.findViewById(R.id.already_user);
		terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

		// Setting text selector over textviews
		@SuppressLint("ResourceType")
		XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			login.setTextColor(csl);
			terms_conditions.setTextColor(csl);
		} catch (Exception e) {
		}
	}

	// Set Listeners
	private void setListeners() {
		signUpButton.setOnClickListener(this);
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signUpBtn:

			// Call checkValidation method
			checkValidation();
			break;

		case R.id.already_user:

			// Replace login fragment
			new LoginActivity().replaceLoginFragment();
			break;
		}

	}

	// Check Validation Method
	private void checkValidation() {

		// Get all edittext texts
		String getFullName = fullName.getText().toString();
		String getLastName = lastName.getText().toString();
		String getEmailId = emailId.getText().toString();
		String getMobileNumber = mobileNumber.getText().toString();
		String getLocation = location.getText().toString();
		String getPassword = password.getText().toString();
		String getConfirmPassword = confirmPassword.getText().toString();

		// Pattern match for email id
		Pattern p = Pattern.compile(Utils.regEx);
		Matcher m = p.matcher(getEmailId);

		// Check if all strings are null or not
		if (getFullName.equals("") || getFullName.length() == 0
				|| getLastName.equals("") || getLastName.length() == 0
				|| getEmailId.equals("") || getEmailId.length() == 0
				|| getMobileNumber.equals("") || getMobileNumber.length() == 0
				|| getLocation.equals("") || getLocation.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0
				|| getConfirmPassword.equals("")
				|| getConfirmPassword.length() == 0)

			new CustomToast().Show_Toast(getActivity(), view,
					"All fields are required.");

		// Check if email id valid or not
		else if (!m.find())
			new CustomToast().Show_Toast(getActivity(), view,
					"Your Email Id is Invalid.");

		// Check if both password should be equal
		else if (!getConfirmPassword.equals(getPassword))
			new CustomToast().Show_Toast(getActivity(), view,
					"Both password doesn't match.");

		// Make sure user should check Terms and Conditions checkbox
		else if (!terms_conditions.isChecked())
			new CustomToast().Show_Toast(getActivity(), view,
					"Please select Terms and Conditions.");

		// Else do signup or do your stuff
		else{
			Log.i("checker",lastName.getText()+"");
			authPresenter.Signup(new SignupUser(emailId.getText().toString(),fullName.getText().toString(),lastName.getText()+"",
					password.getText().toString(),confirmPassword.getText().toString(),
					"2000-12-12",mobileNumber.getText().toString(),"Male"));
		}


	}

	@Override
	public void getMessage(AuthMassage Massage) {
		Toast.makeText(getActivity(),Massage.getMessage(),Toast.LENGTH_LONG).show();
		Intent LoginFragment = new Intent(getContext(), LoginActivity.class);
		getActivity().startActivity(LoginFragment);
	}

	@Override
	public void Login(UserLogin userLogin) {

	}
}