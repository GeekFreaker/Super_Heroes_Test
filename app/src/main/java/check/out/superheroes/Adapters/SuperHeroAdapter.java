package check.out.superheroes.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import check.out.superheroes.Models.SuperHero;
import check.out.superheroes.R;

public class SuperHeroAdapter extends RecyclerView.Adapter<SuperHeroAdapter.SuperHeroViewHolder> {

    private List<SuperHero> mSuperHeroes;
    private Context mContext;
    private View.OnClickListener mListener;
    private int mPosition;

    public SuperHeroAdapter(Context context, List<SuperHero> superHeroes) {
        super();
        mContext = context;
        mSuperHeroes = superHeroes;
    }
    public void msetOnItemClickLister (View.OnClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public SuperHeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_card_view, parent, false);
        return new SuperHeroViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SuperHeroViewHolder holder, int position) {
        mPosition = position;
        holder.HeroName.setText(mSuperHeroes.get(position).getName());
        holder.HeroDescrpition.setText(mSuperHeroes.get(position).getSlug());
        Glide.with(mContext).load(mSuperHeroes.get(position).getImages().getLg()).into(holder.heroFace);
    }

    @Override
    public int getItemCount() {
        return mSuperHeroes.size();
    }

    public class SuperHeroViewHolder extends RecyclerView.ViewHolder {

        private TextView HeroName;
        private TextView HeroDescrpition;
        private ImageView heroFace;
        private View view;

        public SuperHeroViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            HeroName = view.findViewById(R.id.hero_name);
            HeroDescrpition = view.findViewById(R.id.hero_description);
            heroFace = view.findViewById(R.id.hero_photo);
            itemView.setTag(this);
            itemView.setId((int)mSuperHeroes.get(mPosition).getId());
            itemView.setOnClickListener(mListener);
        }
    }
}
