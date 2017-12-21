package com.jaja.home.xmpp.act;

import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jaja.home.xmpp.R;
import com.jaja.home.xmpp.adapter.SearchResultAdapter;
import com.jaja.home.xmpp.entity.UserEntity;
import com.jaja.home.xmpp.util.StringUtil;
import com.jaja.home.xmpp.util.xmpp.XmppUtils;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ${Terry} on 2017/12/21.
 */
public class SearchAct extends BaseActivity implements SearchResultAdapter.OnClickListener {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private SearchResultAdapter adapter;
    private AlertDialog dialog;

    @Override
    protected int getViewId() {
        return R.layout.act_search;
    }

    @Override
    protected void initEvent() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchResultAdapter(mContext);
        adapter.setListener(this);
        mRecyclerView.setAdapter(adapter);


    }


    @OnClick({R.id.btn_ok})
    public void btnClick(View v) {
        String str = etSearch.getText().toString();
        if (StringUtil.isEmpty(str)) {
            Toast.makeText(mContext, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        //搜索用户
        List<UserEntity> userList = XmppUtils.searchUsers(str);
        adapter.setData(userList);
    }

    @Override
    public void onClick(View view, int position) {
        final UserEntity user = adapter.getItem(position);
        View v = LayoutInflater.from(mContext).inflate(R.layout.act_dialog_content, null);
        CircleImageView cv = (CircleImageView) v.findViewById(R.id.dialog_headImv);
        try {
            cv.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("head/" + user.getHead())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView nickName = (TextView) v.findViewById(R.id.dialog_nickName);
        nickName.setText(user.getNickName());
        Button okBtn = (Button) v.findViewById(R.id.dialog_btn_ok);
        Button cancelBtn = (Button) v.findViewById(R.id.dialog_btn_cancel);
        dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_head00)
                .create();
        dialog.setView(v);
        dialog.show();


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean flag = XmppUtils.addGroupFriend("我的好友", user.getUserName());
                if (flag) {
                    Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }
}
