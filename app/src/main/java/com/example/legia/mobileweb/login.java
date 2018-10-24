package com.example.legia.mobileweb;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legia.mobileweb.AdapterSanPham.SanPhamAdapterNew;
import com.example.legia.mobileweb.DAO.generateTokenKey;
import com.example.legia.mobileweb.DAO.resetPasswordDAO;
import com.example.legia.mobileweb.DAO.themVaoGioHang;
import com.example.legia.mobileweb.DAO.userDAO;
import com.example.legia.mobileweb.DTO.User;
import com.example.legia.mobileweb.DTO.resetPass;
import com.example.legia.mobileweb.Encryption.encrypt;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class login extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener  {
    EditText txtUsername, txtPassword;
    Button btnLogin;
    Button btnRegister, btnForgetPass;
    private static final int RC_SIGN_IN = 007;

    GoogleApiClient mGoogleApiClient;

    //Button btnLoginFB, btnLoginGG;
    ProgressDialog pd;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> loginResult;
    //Handles the thread result of the Backup being executed.
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(android.os.Message msg) {
            pd.dismiss();

            if (getIntent().getExtras() != null &&
                    getIntent().getExtras().containsKey("loginGioHang")) {
                Intent intent = new Intent(login.this, MainActivity.class);
                themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
                int count = gioHang.countSoLuongMua();
                intent.putExtra("count", count);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(login.this, MainActivity.class);
                themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
                int count = gioHang.countSoLuongMua();
                intent.putExtra("countGioHang", count);
                startActivity(intent);
            }
            TastyToast.makeText(getApplicationContext(), "Đăng nhập thành công!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        txtUsername = findViewById(R.id.txtUsernameLogin);
        txtPassword = findViewById(R.id.txtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegisterLogin);
        btnForgetPass = findViewById(R.id.btnForgotPassword);
        SignInButton btnLoginGoogle = findViewById(R.id.sign_in_button);


        TextView txtTitle = findViewById(R.id.txtTitle);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "font/Ubuntu.ttf"); // dat fonts
        txtTitle.setTypeface(myTypeface);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                Boolean injectionUsername, injectionPassword;
                username = encrypt.hashWith256(username); // sha256, user1
                password = encrypt.hashWith256(password);

                if(txtUsername.getText().toString().length()==0){
                    txtUsername.setError("Bạn không được để trống");
                }
                if(txtPassword.getText().toString().length()==0){
                    txtPassword.setError("Bạn không được để trống");
                }
                // Check Injection username, password.
                injectionUsername = checkInjection(txtUsername.getText().toString());
                injectionPassword = checkInjection(txtPassword.getText().toString());
                if(injectionUsername==true){
                    txtUsername.setError("Bạn không được nhập ký tự đặc biệt");
                }
                if(injectionPassword==true){
                    txtPassword.setError("Bạn không được nhập ký tự đặc biệt");
                }
                else{
                    // OK
                    User u = login(username, password);
                    if(u != null){
                        SharedPreferences sp = getSharedPreferences("userLogin", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username", u.getTen_user());
                        editor.putString("password", password);
                        editor.putBoolean("isLogin", true);
                        editor.putInt("idUser", u.getIduser());
                        editor.commit();


                        pd = ProgressDialog.show(login.this,"Vui lòng đợi...", "Đang đăng nhập...", false, true);
                        pd.setCanceledOnTouchOutside(false);
                        Thread t = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    sleep(3000);  //Delay of 3 seconds
                                }
                                catch (Exception e) {}
                                handler.sendEmptyMessage(0);
                            }
                        } ;
                        t.start();


                    }
                    else {
                        //Toast.makeText(login.this, "ERROR", Toast.LENGTH_SHORT).show();
                        TastyToast.makeText(getApplicationContext(), "Không đúng username hoặc password", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                }

            }
        });
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton btnLoginButton = findViewById(R.id.btnLoginFB);
        btnLoginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        btnLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("Main", response.toString());
                                setProfileToView(object);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                // Save status
                SharedPreferences sp = getSharedPreferences("loginWithFb", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isLoginWithFB", true);
                editor.commit();

                request.setParameters(parameters);
                request.executeAsync();
                startActivity(new Intent(login.this, MainActivity.class));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* activity hiện tại */, this /* kết quả trả về khi kết nối lỗi */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(login.this, register.class));
            }
        });

        btnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(login.this);
                dialog.setContentView(R.layout.layout_forget);
                dialog.setTitle("Bạn quên mật khẩu ?");
                dialog.show();

                Button btnRecovery =  dialog.findViewById(R.id.btnRecoveryPassword);
                final EditText txtUserForgot = dialog.findViewById(R.id.txtUsernameForget);
                final EditText txtEmailForgot = dialog.findViewById(R.id.txtEmailForget);
                btnRecovery.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                        String usernameForgot = txtUserForgot.getText().toString();
                        String emailForgot = txtEmailForgot.getText().toString();
                        if(usernameForgot.length()==0){
                            txtUserForgot.setError("Không được bỏ trống");
                            return;
                        }
                        else if(emailForgot.length()==0){
                            txtEmailForgot.setError("Không được bỏ trống");
                            return;
                        }
                        else{
                            User thanhVienQuenMatKhau = userDAO.ResetPassword(encrypt.hashWith256(usernameForgot), emailForgot);
                            if(thanhVienQuenMatKhau != null){
                                // tìm thấy tên và email thành viên này trong csdl
                                final String admin = "testmailbaitap@gmail.com";
                                final String password_admin = "Vuhoangnguyen";

                                Properties props = new Properties();
                                props.put("mail.smtp.auth", "true");
                                props.put("mail.smtp.starttls.enable", "true");
                                props.put("mail.smtp.host", "smtp.gmail.com");
                                props.put("mail.smtp.port", "587");

                                Session session = Session.getInstance(props,
                                    new javax.mail.Authenticator() {
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                            return new PasswordAuthentication(admin, password_admin);
                                        }
                                    }
                                );

                                try {
                                    String tokenKey = generateTokenKey.generateToken(thanhVienQuenMatKhau.getUsername());
                                    int addToken = resetPasswordDAO.themToken(thanhVienQuenMatKhau.getIduser(), tokenKey);
                                    if(addToken!=0){
                                        Message message = new MimeMessage(session);
                                        message.setHeader("Content-Type", "text/html; charset=UTF-8");
                                        message.setFrom(new InternetAddress("testmailbaitap@gmail.com"));
                                        message.setRecipients(Message.RecipientType.TO,
                                                InternetAddress.parse(emailForgot));
                                        message.setSubject("Khôi phục mật khẩu");
                                        String noiDung = "Chào bạn ," + thanhVienQuenMatKhau.getTen_user()
                                                + "<br/><strong> Mã token key bạn là: " + tokenKey +"</strong>"
                                                + "\n\n Email của bạn: " + emailForgot
                                                + "\n\n Bạn vui lòng nhập mã token đó vào ô reset mật khẩu."
                                                + "\n\n Thân! "
                                                + "\n\n Admin";
                                        message.setContent(noiDung, "text/html; charset=utf-8");

                                        Transport.send(message);

                                        System.out.println("Done");
                                        // xử lý thread trong quá trình gửi mail
                                        // sau khi gởi xong thì in ra thông báo
                                        AlertDialog.Builder alertDialog=new AlertDialog.Builder(login.this);
                                        alertDialog.setTitle("Đã gửi mã xác nhận về email");
                                        alertDialog.setMessage("Bạn có đổi mật khẩu ngay ?");
                                        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                dialog.cancel();
                                            }
                                        });
                                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Dialog recovery = new Dialog(login.this);
                                                recovery.setContentView(R.layout.layout_checktoken);
                                                recovery.setTitle("Kiểm tra mã xác thực ");
                                                recovery.show();

                                                Button btnCheckToken =  recovery.findViewById(R.id.btnCheckToken);
                                                final EditText txtMaXacNhan = recovery.findViewById(R.id.txtMaXacNhan);


                                                btnCheckToken.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String tokenKey = txtMaXacNhan.getText().toString();
                                                        long checkTime = resetPasswordDAO.checkToken(tokenKey);
                                                        final resetPass resetPass= resetPasswordDAO.readTokenKey(tokenKey);
                                                        final int iduser = resetPass.getIduser();
                                                        Log.i("test", "Check time: " + checkTime + "iduser: " + iduser);
                                                        if(checkTime>0){
                                                            Toast.makeText(login.this, "CÒN HIỆU LỰC", Toast.LENGTH_SHORT).show();
                                                            Dialog recovery = new Dialog(login.this);
                                                            recovery.setContentView(R.layout.layout_recovery);
                                                            recovery.setTitle("Khôi phục mật khẩu ");
                                                            recovery.show();
                                                            Log.i("test", "id user: " + iduser);
                                                            Log.i("test1", "token: " + tokenKey);



                                                            Button btnRecoveryPassword =  recovery.findViewById(R.id.btnRecoveryPassword);
                                                            final TextView txtUsernameRecovery = recovery.findViewById(R.id.txtUsernnameRecovery);
                                                            final EditText txtPasswordNew = recovery.findViewById(R.id.txtPasswordRecovery);
                                                            final EditText txtPasswordNewCheck = recovery.findViewById(R.id.txtPasswordRecoveryCheck);

                                                            User user = userDAO.readUser(iduser);

                                                            txtUsernameRecovery.setText("Tên user: " + user.getTen_user());

                                                            btnRecoveryPassword.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    // Check
                                                                    String newPassword = encrypt.hashWith256(txtPasswordNew.getText().toString());
                                                                    String repeatPassword = encrypt.hashWith256(txtPasswordNewCheck.getText().toString());
                                                                    if(newPassword.equals(repeatPassword)){
                                                                        int update = userDAO.updatePassword(iduser, newPassword);
                                                                        if(update!=0){
                                                                            int delete = resetPasswordDAO.deleteToken(iduser);
                                                                            TastyToast.makeText(login.this, "Đổi mật khẩu thành công", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                                                                            startActivity(new Intent(login.this, login.class));
                                                                        }
                                                                    }
                                                                    else{
                                                                        TastyToast.makeText(login.this, "Mật khẩu phải giống nhau", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                                                                        return;
                                                                    }
                                                                }
                                                            });
                                                        }
                                                        else{
                                                            int delete = resetPasswordDAO.deleteToken(iduser);
                                                            AlertDialog.Builder alertDialog=new AlertDialog.Builder(login.this);
                                                            alertDialog.setTitle("Hết thời gian cho phép");
                                                            alertDialog.setMessage("Mã xác nhận này đã quá thời gian cho phép \n Bạn vui lòng thực hiện lại");
                                                            alertDialog.setPositiveButton("Trở lại", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                }
                                                            });
                                                            alertDialog.show();
                                                        }
                                                    }
                                                });
                                            }
                                        });

                                        alertDialog.show();
                                    }
                                    else{
                                        // check if exist
                                        AlertDialog.Builder alertDialog=new AlertDialog.Builder(login.this);
                                        alertDialog.setTitle("Vui lòng kiểm tra lại email.");
                                        alertDialog.setMessage("Chúng tôi đã gửi bạn mã xác nhận \n Vui lòng kiểm tra lại email.");
                                        alertDialog.setPositiveButton("Khôi phục mật khẩu", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Dialog recovery = new Dialog(login.this);
                                                recovery.setContentView(R.layout.layout_checktoken);
                                                recovery.setTitle("Kiểm tra mã xác thực ");
                                                recovery.show();

                                                Button btnCheckToken =  recovery.findViewById(R.id.btnCheckToken);
                                                final EditText txtMaXacNhan = recovery.findViewById(R.id.txtMaXacNhan);

                                                btnCheckToken.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String tokenKey = txtMaXacNhan.getText().toString();
                                                        long checkTime = resetPasswordDAO.checkToken(tokenKey);
                                                        final resetPass resetPass= resetPasswordDAO.readTokenKey(tokenKey);
                                                        final int iduser = resetPass.getIduser();
                                                        Log.i("test", "Check time: " + checkTime + "iduser: " + iduser);
                                                        if(checkTime>0){
                                                            Toast.makeText(login.this, "CÒN HIỆU LỰC", Toast.LENGTH_SHORT).show();
                                                            Dialog recovery = new Dialog(login.this);
                                                            recovery.setContentView(R.layout.layout_recovery);
                                                            recovery.setTitle("Khôi phục mật khẩu ");
                                                            recovery.show();
                                                            Log.i("test", "id user: " + iduser);
                                                            Log.i("test1", "token: " + tokenKey);



                                                            Button btnRecoveryPassword =  recovery.findViewById(R.id.btnRecoveryPassword);
                                                            final TextView txtUsernameRecovery = recovery.findViewById(R.id.txtUsernnameRecovery);
                                                            final EditText txtPasswordNew = recovery.findViewById(R.id.txtPasswordRecovery);
                                                            final EditText txtPasswordNewCheck = recovery.findViewById(R.id.txtPasswordRecoveryCheck);

                                                            User user = userDAO.readUser(iduser);

                                                            txtUsernameRecovery.setText("Tên user: " + user.getTen_user());

                                                            btnRecoveryPassword.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    // Check
                                                                    String newPassword = encrypt.hashWith256(txtPasswordNew.getText().toString());
                                                                    String repeatPassword = encrypt.hashWith256(txtPasswordNewCheck.getText().toString());
                                                                    if(newPassword.equals(repeatPassword)){
                                                                        int update = userDAO.updatePassword(iduser, newPassword);
                                                                        if(update!=0){
                                                                            int delete = resetPasswordDAO.deleteToken(iduser);
                                                                            TastyToast.makeText(login.this, "Đổi mật khẩu thành công", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                                                                            startActivity(new Intent(login.this, login.class));
                                                                        }
                                                                    }
                                                                    else{
                                                                        TastyToast.makeText(login.this, "Mật khẩu phải giống nhau", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                                                                        return;
                                                                    }
                                                                }
                                                            });
                                                        }
                                                        else{
                                                            int delete = resetPasswordDAO.deleteToken(iduser);
                                                            AlertDialog.Builder alertDialog=new AlertDialog.Builder(login.this);
                                                            alertDialog.setTitle("Hết thời gian cho phép");
                                                            alertDialog.setMessage("Mã xác nhận này đã quá thời gian cho phép \n Bạn vui lòng thực hiện lại");
                                                            alertDialog.setPositiveButton("Trở lại", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                }
                                                            });
                                                            alertDialog.show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                        alertDialog.show();
                                    }



                                } catch (MessagingException e) {

                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                });

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();

                SharedPreferences sp = getSharedPreferences("loginWithGoogle", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isLoginWithGoogle", true);
                editor.putString("name", personGivenName);
                editor.commit();
                Log.i("google acc: ", personEmail + " " + personName);
            }
            startActivity(new Intent(login.this, MainActivity.class));
        } else {
            // Signed out, show unauthenticated UI.
            //  updateUI(false);
        }
    }

    private User login(String username, String password){
        User u = userDAO.Login(username,password);
        return u;
    }

    private Boolean checkInjection(String s) {
        Boolean injection = false;
        Pattern p = Pattern.compile("'(''|[^'])*'");
        Matcher m = p.matcher(s);
        boolean b = m.find();
        if (b == true)
            injection = true;
        else
            injection = false;

        return injection;
    }

    private void setProfileToView(JSONObject jsonObject) {
        try {
            String name = jsonObject.getString("name");
            String idFB = jsonObject.getString("id");
            SharedPreferences userFB = getSharedPreferences("userFB", MODE_PRIVATE);
            SharedPreferences.Editor editor = userFB.edit();
            editor.putString("nameFB", name);
            editor.putString("facebookID", idFB);
            editor.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
