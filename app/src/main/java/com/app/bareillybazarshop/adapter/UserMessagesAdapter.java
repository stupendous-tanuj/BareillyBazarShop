package com.app.bareillybazarshop.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.activity.BaseActivity;
import com.app.bareillybazarshop.api.output.UserMessages;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

import java.util.List;

/**
 * Created by TANUJ on 5/2/2016.
 */
public class UserMessagesAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private BaseActivity activity;
    private List<UserMessages> userMessages;

    public UserMessagesAdapter(BaseActivity activity, List<UserMessages> userMessages) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.userMessages = userMessages;
    }


    @Override
    public int getCount() {
        return userMessages != null ? userMessages.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return userMessages != null ? userMessages.get(position) : null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_user_messages, viewGroup, false);
            holder = new ViewHolder();
            holder.linear_customer_messages = (LinearLayout) convertView.findViewById(R.id.linear_customer_messages);
            holder.tv_userId = (TextView) convertView.findViewById(R.id.tv_userId);
            holder.tv_message = (TextView) convertView.findViewById(R.id.tv_message);
            holder.tv_call = (TextView) convertView.findViewById(R.id.tv_call);
            holder.tv_email = (TextView) convertView.findViewById(R.id.tv_email);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setData(userMessages.get(pos), holder, pos);

        return convertView;
    }

    private void setData(final UserMessages userMessages, final ViewHolder holder, final int pos) {
        holder.tv_userId.setText(userMessages.getUserId());
        holder.tv_message.setText(userMessages.getUserMessage());

        holder.tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/html");
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{userMessages.getUserEmailId()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Query Response From "+activity.getString(R.string.app_name));
                activity.startActivity(Intent.createChooser(intent, "Send Email"));

            }
        });

        holder.tv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.callPhone(userMessages.getUserMobileNumber());
            }
        });
    }
    public class ViewHolder {
        private TextView tv_userId;
        private TextView tv_message;
        private TextView tv_call;
        private TextView tv_email;
        public LinearLayout linear_customer_messages;
    }
}

