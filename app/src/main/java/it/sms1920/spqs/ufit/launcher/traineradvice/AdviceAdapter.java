package it.sms1920.spqs.ufit.launcher.traineradvice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.sms1920.spqs.ufit.launcher.R;
public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.AdviceHolder> implements iAdvice.View {

    private iAdvice.Presenter presenter;

    private AdviceFragment parentFragment;

    public AdviceAdapter(AdviceFragment parentFragment) {
        presenter = new AdviceList(this);
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public AdviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdviceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advice, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull AdviceHolder holder, int position) {
        presenter.onBindAdviceItemListViewAtPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getAdviceCount();
    }

    @Override
    public void callNotifyDataSetChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void addNewAdvice(String title, String description) {
        presenter.addNewAdviceItem(title,description,"-1");
    }

    public class AdviceHolder extends RecyclerView.ViewHolder implements iAdvice.View.Item {
        private TextView tvTitle;
        private TextView tvDescription;
        private Button btnDeleteAdvice;
        private int position;
        private String author;
        private String adviceId;

        public AdviceHolder(@NonNull final View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleAdvice);
            tvDescription = itemView.findViewById(R.id.tvDescriptionAdvice);
            btnDeleteAdvice = itemView.findViewById(R.id.btnDeleteAdvice);
            btnDeleteAdvice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onDeleteClicked(position, adviceId);
                    Toast.makeText(itemView.getContext(),"Consiglio eliminato con successo", Toast.LENGTH_LONG);
                }
            });
        }

        @Override
        public void setTitle(String title) {
            tvTitle.setText(title);
        }

        @Override
        public void setDescription(String description) {
            tvDescription.setText(description);
        }

        @Override
        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void setAuthor(String author) {
            this.author = author;
        }

        @Override
        public void setAdviceId(String adviceId) {
            this.adviceId = adviceId;
        }

    }
}
