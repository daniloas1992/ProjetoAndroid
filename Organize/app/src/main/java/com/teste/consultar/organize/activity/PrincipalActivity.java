package com.teste.consultar.organize.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.github.clans.fab.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.teste.consultar.organize.R;
import com.teste.consultar.organize.adapter.AdapterMovimentacao;
import com.teste.consultar.organize.config.ConfiguracaoFirebase;
import com.teste.consultar.organize.helpers.Base64Custom;
import com.teste.consultar.organize.model.Movimentacao;
import com.teste.consultar.organize.model.Usuario;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton menu_fab_despesa, menu_fab_receita;
    private MaterialCalendarView calendarView;
    private TextView tvPrincipalSaudacao, tvPrincipalSaldo, tvPrincipalSaldoGeral;
    RecyclerView recyMovimentos;
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAuthInstance();
    private DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabaseInstance();
    private DatabaseReference usuarioReference;
    private DatabaseReference movimentacaoReference;
    private ValueEventListener valueEventListenerUsuario;
    private ValueEventListener valueEventListenerMovimentacoes;
    private AdapterMovimentacao adapterMovimentacao;
    private List<Movimentacao> movimentacoes = new ArrayList<>();
    private Movimentacao movimentacao;
    private String mesAnoSelecionado = "";
    private Double despesaTotal = 0.0;
    private Double receitaTotal = 0.0;
    private Double resumoSaldo = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.organize);
        setSupportActionBar(toolbar);

        menu_fab_despesa = findViewById(R.id.menu_fab_despesa);
        menu_fab_receita = findViewById(R.id.menu_fab_receita);
        calendarView = findViewById(R.id.calendarView);
        tvPrincipalSaudacao = findViewById(R.id.tvPrincipalSaudacao);
        tvPrincipalSaldo = findViewById(R.id.tvPrincipalSaldo);
        tvPrincipalSaldoGeral = findViewById(R.id.tvPrincipalSaldoGeral);
        recyMovimentos = findViewById(R.id.recyMovimentos);

        menu_fab_despesa.setOnClickListener(this);
        menu_fab_receita.setOnClickListener(this);
        
        configurarCalendarView();
        configurarSwipe();

        //Configruar adapter:
        adapterMovimentacao = new AdapterMovimentacao(movimentacoes, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyMovimentos.setLayoutManager(layoutManager);
        recyMovimentos.setHasFixedSize(true);
        recyMovimentos.setAdapter(adapterMovimentacao);


    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarInformacoesUsuario();
        carregarMovimentacoes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSair:
                autenticacao.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_fab_despesa:
                adicionarDespesa();
                break;
            case R.id.menu_fab_receita:
                adicionarReceita();
                break;
        }
    }

    private void configurarCalendarView() {
        CharSequence meses[] = {"Janeiro","Fevereio", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        calendarView.setTitleMonths(meses);

        CalendarDay currentDate = calendarView.getCurrentDate();

        mesAnoSelecionado = String.format("%02d%s", (currentDate.getMonth() + 1), String.valueOf(currentDate.getYear()));

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                mesAnoSelecionado =  String.format("%02d%s", (date.getMonth() + 1), String.valueOf(date.getYear()));

                movimentacaoReference.removeEventListener(valueEventListenerMovimentacoes); // Remove o listener do mês anterior
                carregarMovimentacoes();
            }
        });
    }

    private void carregarInformacoesUsuario() {
        final String emailUsuarioFirebase = autenticacao.getCurrentUser().getEmail();
        final String idUsuarioFirebase = Base64Custom.encodeBase64(emailUsuarioFirebase);

        usuarioReference = databaseReference.child(Usuario.CHILD_USUARIOS_FIREBASE).child(idUsuarioFirebase);

        valueEventListenerUsuario = usuarioReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                DecimalFormat decimalFormat = new DecimalFormat("0.00");

                despesaTotal = usuario.getDespesaTotal();
                receitaTotal = usuario.getReceitaTotal();
                resumoSaldo = receitaTotal - despesaTotal;

                tvPrincipalSaudacao.setText(String.format("Olá %s ",usuario.getNome()));
                tvPrincipalSaldo.setText(String.format("R$ %s ",decimalFormat.format(resumoSaldo)));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void carregarMovimentacoes() {
        final String emailUsuarioFirebase = autenticacao.getCurrentUser().getEmail();
        final String idUsuarioFirebase = Base64Custom.encodeBase64(emailUsuarioFirebase);

        movimentacaoReference = databaseReference.child(Movimentacao.CHILD_MOVIMENTACAO_FIREABSE)
                                                 .child(idUsuarioFirebase)
                                                 .child(mesAnoSelecionado);

        valueEventListenerMovimentacoes = movimentacaoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                movimentacoes.clear();

                for(DataSnapshot dados: dataSnapshot.getChildren()) {

                    Movimentacao movimentacao = dados.getValue(Movimentacao.class);
                    movimentacao.setChaveID(dados.getKey());
                    movimentacoes.add(movimentacao);
                }

                adapterMovimentacao.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void configurarSwipe() {

        ItemTouchHelper.Callback itemTouchHelper = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
               int dragFlags =  ItemTouchHelper.ACTION_STATE_IDLE; // Desabilitar drag and drop
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END; // Swipe para direita ou para esquerda

                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                excluirMovimentacao(viewHolder);
            }
        };

        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyMovimentos);

    }

    private void excluirMovimentacao(final RecyclerView.ViewHolder viewHolder) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Excluir registro");
        alertDialog.setMessage("Você tem certeza que deseja realmente excluir o registro seleiconado?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final int position = viewHolder.getAdapterPosition();

                movimentacao = movimentacoes.get(position);

                final String emailUsuarioFirebase = autenticacao.getCurrentUser().getEmail();
                final String idUsuarioFirebase = Base64Custom.encodeBase64(emailUsuarioFirebase);

                movimentacaoReference = databaseReference.child(Movimentacao.CHILD_MOVIMENTACAO_FIREABSE)
                                                         .child(idUsuarioFirebase)
                                                         .child(mesAnoSelecionado);

                movimentacaoReference.child(movimentacao.getChaveID()).removeValue();
                adapterMovimentacao.notifyItemRemoved(position);
                atualizarSaldo();

            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(PrincipalActivity.this, "Cancelado!", Toast.LENGTH_SHORT).show();
                adapterMovimentacao.notifyDataSetChanged();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private void atualizarSaldo() {

        final String emailUsuarioFirebase = autenticacao.getCurrentUser().getEmail();
        final String idUsuarioFirebase = Base64Custom.encodeBase64(emailUsuarioFirebase);

        usuarioReference = databaseReference.child(Usuario.CHILD_USUARIOS_FIREBASE).child(idUsuarioFirebase);

        if(movimentacao.getTipo().equals(Movimentacao.TIPO_RECEITA)){
            receitaTotal = receitaTotal - movimentacao.getValor();
            usuarioReference.child(Usuario.CHILD_USUARIOS_RECEITATOTAL_FIREBASE).setValue(receitaTotal);
        }

        if(movimentacao.getTipo().equals(Movimentacao.TIPO_DESPESA)) {
            despesaTotal = despesaTotal - movimentacao.getValor();
            usuarioReference.child(Usuario.CHILD_USUARIOS_DESPESATOTAL_FIREBASE).setValue(despesaTotal);
        }
    }

    private void adicionarDespesa() {
        startActivity(new Intent(this, DespesasActivity.class));
    }

    private void adicionarReceita() {
        startActivity(new Intent(this, ReceitasActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioReference.removeEventListener(valueEventListenerUsuario);
        movimentacaoReference.removeEventListener(valueEventListenerMovimentacoes);
    }
}