package com.example.lutemon;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private ArrayList<Lutemon> mons;
    public CustomAdapter(ArrayList<Lutemon> mons) {this.mons = mons;}

    private OnItemClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View profileCircle;
        private final TextView monName;
        private final TextView monAtk;
        private final TextView monDef;
        private final TextView monExp;
        private final TextView monHp;

        public ViewHolder(View v) {
            super(v);
            this.profileCircle=v.findViewById(R.id.profileCircle);
            this.monName=v.findViewById(R.id.monName);
            this.monAtk=v.findViewById(R.id.monAtk);
            this.monDef=v.findViewById(R.id.monDef);
            this.monExp=v.findViewById(R.id.monExp);
            this.monHp=v.findViewById(R.id.monHp);
        }

        public View getProfileCircle() { return  this.profileCircle; }
        public TextView getMonName() { return this.monName; }
        public TextView getMonAtk() { return this.monAtk; }
        public TextView getMonDef() { return this.monDef; }
        public TextView getMonExp() { return this.monExp; }
        public TextView getMonHp() { return this.monHp; }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_small, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getMonName().setText(mons.get(position).getName());
        viewHolder.getMonAtk().setText(mons.get(position).getAtk()+" ATK");
        viewHolder.getMonDef().setText(mons.get(position).getDef()+" DEF");
        viewHolder.getMonExp().setText(mons.get(position).getExp()+" EXP");
        viewHolder.getMonHp().setText(mons.get(position).getHp()+" HP");
        GradientDrawable drawable = (GradientDrawable) viewHolder.getProfileCircle().getBackground();
        drawable.setColor(Color.parseColor(mons.get(position).getColor()));

        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(viewHolder.getAdapterPosition());
            }
        });
    }
    public int getItemCount() { return mons.size(); }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
