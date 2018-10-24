package com.example.legia.mobileweb.AdapterHeThong;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.legia.mobileweb.AdapterSanPham.ListGopY;
import com.example.legia.mobileweb.DTO.gopY;
import com.example.legia.mobileweb.DTO.menuMain;
import com.example.legia.mobileweb.MainActivity;
import com.example.legia.mobileweb.R;
import com.example.legia.mobileweb.grid_order;
import com.example.legia.mobileweb.hethong;
import com.example.legia.mobileweb.login;
import com.example.legia.mobileweb.memberinfo;
import com.example.legia.mobileweb.register;
import com.example.legia.mobileweb.scancode;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class AdapterMenuMain extends BaseAdapter implements
        GoogleApiClient.OnConnectionFailedListener {
    private Context context;
    private List<menuMain> listMenu;
    GoogleApiClient mGoogleApiClient;

    public AdapterMenuMain(Context context, List<menuMain> listMenu) {
        this.context = context;
        this.listMenu = listMenu;
    }

    @Override
    public int getCount() {
        return listMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return listMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_menu_main,parent,false);
        }

        TextView txtMenu = convertView.findViewById(R.id.txtMenuMain);

        menuMain menuMain = listMenu.get(position);

        final int idMenu = menuMain.getIdMenu();
        txtMenu.setText(menuMain.getTenMenu());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (idMenu){
                    case 2: // góp ý
                        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                        sendIntent.setType("text/plain");
                        sendIntent.setData(Uri.parse("mailto:testmailbaitap@gmail.com"));
                        //sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "[Góp ý phần mềm]");
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                " Phiên bản android hiện tại: " + Build.VERSION.RELEASE
                                        +"\n Tên điện thoại: " + Build.MODEL
                                        +"\n API hiện tại: " + Build.VERSION.SDK_INT
                                        +"\n --------------------------------------" +
                                        "\n Nội dung góp ý: ");
                        context.startActivity(sendIntent);
                        break;
                    case 3: // hệ thống cửa hàng
                        context.startActivity(new Intent(context, hethong.class));
                        break;
                    case 4: // thẻ tích điểm
                        LayoutInflater liMember;
                        android.support.v7.app.AlertDialog.Builder builderMember;
                        android.support.v7.app.AlertDialog alertDialogMember;
                        final View viewCardMemBer;

                        liMember = LayoutInflater.from(context);
                        viewCardMemBer = liMember.inflate(R.layout.layout_listmembercard, null);

                        List<Integer> dsCard = new ArrayList<>();
                        dsCard.add(R.drawable.bronze);
                        dsCard.add(R.drawable.silver);
                        dsCard.add(R.drawable.gold);
                        dsCard.add(R.drawable.platinum);
                        dsCard.add(R.drawable.diamond);

                        ListView listMemberCard = viewCardMemBer.findViewById(R.id.listMemberCard);
                        AdapterMemberCard dataAdapter = new AdapterMemberCard(context, dsCard);

                        listMemberCard.setAdapter(dataAdapter);

                        builderMember = new android.support.v7.app.AlertDialog.Builder(context);
                        builderMember.setView(viewCardMemBer);
                        builderMember.setCancelable(false);
                        builderMember.setPositiveButton("Quay về", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });

                        alertDialogMember = builderMember.create();
                        alertDialogMember.show();

                        break;
                    case 5: // chính sách hậu mãi
                        LayoutInflater liChinhSach;
                        android.support.v7.app.AlertDialog.Builder builderChinhSach;
                        android.support.v7.app.AlertDialog alertDialogChinhSach;
                        final View viewCardChinhSach;

                        liMember = LayoutInflater.from(context);
                        viewCardChinhSach = liMember.inflate(R.layout.layout_chinhsach, null);
                        String html = "<iframe src=\"https://onedrive.live.com/embed?cid=DD384BAE62E2801B&resid=DD384BAE62E2801B%21218&authkey=AKaInXACyRD48Cc&em=2\" width=\"476\" height=\"288\" frameborder=\"0\" scrolling=\"no\"></iframe>";
                        WebView webChinhSach = viewCardChinhSach.findViewById(R.id.webChinhSach);
                        WebSettings webSettings = webChinhSach.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webSettings.setSupportZoom(true);
                        webSettings.setBuiltInZoomControls(true);
                        webChinhSach.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);

                        builderMember = new android.support.v7.app.AlertDialog.Builder(context);
                        builderMember.setView(viewCardChinhSach);
                        builderMember.setCancelable(false);
                        builderMember.setPositiveButton("Quay về", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });

                        alertDialogMember = builderMember.create();
                        alertDialogMember.show();

                        break;
                    case 6: // đăng nhập
                        Intent loginIntent = new Intent(context, login.class);
                        context.startActivity(loginIntent);
                        break;
                    case 7: // đăng xuất
                        AlertDialog.Builder messageBox = new AlertDialog.Builder(context);
                        messageBox.setTitle("Đăng xuất?");
                        messageBox.setMessage("Bạn có muốn đăng xuất không?");
                        messageBox.setCancelable(false);
                        messageBox.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences user = context.getSharedPreferences("userLogin",
                                        context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = user.edit();
                                editor.clear();
                                editor.commit();

                                SharedPreferences gioHang = context.getSharedPreferences("shareGioHang", context.MODE_PRIVATE);
                                SharedPreferences.Editor gioHangEdit = gioHang.edit();
                                gioHangEdit.clear();
                                gioHangEdit.commit();

                                Intent intent = new Intent(context,
                                        MainActivity.class);
                                context.startActivity(intent);

                            }
                        });
                        messageBox.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = messageBox.create();
                        alertDialog.show();

                        break;
                    case 8:
                        context.startActivity(new Intent(context, memberinfo.class));
                        break;
                    case 9:
                        context.startActivity(new Intent(context, scancode.class));
                        break;
                    case 10:
                        String url = "https://unghotoi.com/mobileshopou";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                        break;
                    case 11:
                        AlertDialog.Builder messageBoxFacebookLogout = new AlertDialog.Builder(context);
                        messageBoxFacebookLogout.setTitle("Đăng xuất khỏi Facebook?");
                        messageBoxFacebookLogout.setMessage("Bạn có muốn đăng xuất không?");
                        messageBoxFacebookLogout.setCancelable(false);
                        messageBoxFacebookLogout.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (AccessToken.getCurrentAccessToken() == null) {
                                    return; // already logged out
                                }

                                new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                                        .Callback() {
                                    @Override
                                    public void onCompleted(GraphResponse graphResponse) {

                                        LoginManager.getInstance().logOut();

                                    }
                                }).executeAsync();
                                SharedPreferences sp = context.getSharedPreferences("loginWithFb", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.clear();
                                editor.commit();
                                context.startActivity(new Intent(context, MainActivity.class));
                            }
                        });
                        messageBoxFacebookLogout.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        AlertDialog facebook = messageBoxFacebookLogout.create();
                        facebook.show();


                        break;
                    case 12:
                        AlertDialog.Builder messageBoxGoogleLogout = new AlertDialog.Builder(context);
                        messageBoxGoogleLogout.setTitle("Đăng xuất khỏi Google?");
                        messageBoxGoogleLogout.setMessage("Bạn có muốn đăng xuất không?");
                        messageBoxGoogleLogout.setCancelable(false);
                        messageBoxGoogleLogout.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestEmail()
                                        .build();

                                mGoogleApiClient = new GoogleApiClient.Builder(context)
                                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                                        .build();
                                mGoogleApiClient.disconnect();
                                SharedPreferences loginGoogle = context.getSharedPreferences("loginWithGoogle", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editorloginGoogle = loginGoogle.edit();
                                editorloginGoogle.clear();
                                editorloginGoogle.commit();
                                context.startActivity(new Intent(context, MainActivity.class));
                            }
                        });
                        messageBoxGoogleLogout.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        AlertDialog google = messageBoxGoogleLogout.create();
                        google.show();


                        break;
                    case 13:
                        //Facebook
                        AlertDialog.Builder messageBoxFacebook = new AlertDialog.Builder(context);
                        messageBoxFacebook.setTitle("Đăng ký thành viên?");
                        messageBoxFacebook.setMessage("Bạn có đăng ký thành viên của hệ thống để được tích điểm giảm giá không ?");
                        messageBoxFacebook.setCancelable(false);
                        messageBoxFacebook.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                context.startActivity(new Intent(context, register.class));
                            }
                        });
                        messageBoxFacebook.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialogFacebook = messageBoxFacebook.create();
                        alertDialogFacebook.show();
                        break;
                    case 14:
                        // Google
                        AlertDialog.Builder messageBoxGoogle = new AlertDialog.Builder(context);
                        messageBoxGoogle.setTitle("Đăng ký thành viên?");
                        messageBoxGoogle.setMessage("Bạn có đăng ký thành viên của hệ thống để được tích điểm giảm giá không ?");
                        messageBoxGoogle.setCancelable(false);
                        messageBoxGoogle.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                context.startActivity(new Intent(context, register.class));
                            }
                        });
                        messageBoxGoogle.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialogGoogle = messageBoxGoogle.create();
                        alertDialogGoogle.show();
                        break;
                    case 15:
                        // Pre-Order.
                        context.startActivity(new Intent(context, grid_order.class));
                        break;

                }

            }
        });

        return convertView;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
