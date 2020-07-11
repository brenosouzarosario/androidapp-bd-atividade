package com.example.bd_atividade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    // Atributos da tela
    private EditText edtCodigo;
    private EditText edtNomeAtividade;
    private EditText edtResponsavel;
    private EditText edtPrioridade;
    private ListView livAtividade;
    private BottomNavigationView btn_navegacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Relacionando os objetos da tela com os objetos de programação
        edtCodigo=findViewById(R.id.edtCodAtividade);
        edtNomeAtividade=findViewById(R.id.edtNomeAtividade);
        edtResponsavel=findViewById(R.id.edtResponsavel);
        edtPrioridade=findViewById(R.id.edtPrioridade);
        livAtividade=findViewById(R.id.lstAtividade);
        btn_navegacao=findViewById(R.id.btnNavegacao);

        //Ativar o listener do botão de navegação
        this.btn_navegacao.setOnNavigationItemSelectedListener(btn_navegacao);

        //Ativar listener da listView
        this.livAtividade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Atividade atividade = null;
                edtCodigo.setText((CharSequence) String.valueOf(atividade.getCodigo()));
                edtNomeAtividade.setText((CharSequence) atividade.getNomeAtividade());
                edtResponsavel.setText((CharSequence) atividade.getResponsavel());
                edtPrioridade.setText((CharSequence) String.valueOf(atividade.getPrioridade()));
            }
        });

        inicializarFireBase();
    }


    public void inicializarFireBase()
    {
        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseDatabase database= FirebaseDatabase.getInstance();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nav_incluir:
                            Atividade atividade = new Atividade();
                            atividade.setCodigo(Integer.valueOf(edtCodigo.getText().toString()));
                            atividade.setNomeAtividade(edtNomeAtividade.getText().toString());
                            atividade.setResponsavel(edtResponsavel.getText().toString());
                            atividade.setPrioridade(Integer.valueOf(edtPrioridade.getText().toString()));
                            DatabaseReference databaseReference;
                            databaseReference.child("atividade").child(String.valueOf(atividade.getCodigo())).setValue(atividade);
                            pesquisarTodos();
                            break;
                        case R.id.nav_pesquisar:
                            Integer codigo = Integer.valueOf(edtCodigo.getText().toString());
                            pesquisar(codigo);
                            break;
                        case R.id.nav_pesquisar_todos:
                            pesquisarTodos();
                            break;
                        case R.id.nav_atualizar:
                            atividade = new Atividade();
                            atividade.setCodigo(Integer.valueOf(edtCodigo.getText().toString()));
                            atividade.setNomeAtividade(edtNomeAtividade.getText().toString());
                            atividade.setResponsavel(edtResponsavel.getText().toString());
                            atividade.setPrioridade(Integer.valueOf(edtPrioridade.getText().toString()));
                            databaseReference.child("atividade").child(String.valueOf(atividade.getCodigo())).setValue(atividade);
                            pesquisarTodos();
                            break;
                        case R.id.nav_excluir:
                            codigo = Integer.valueOf(edtCodigo.getText().toString());
                            databaseReference.child("atividade").child(String.valueOf(codigo)).removeValue();
                            pesquisarTodos();
                            break;
                    }
                    return true;
                }
            };

    public void pesquisarTodos(){
        databaseReference.child("atividade").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaAtividade.clear();
                for (DataSnapshot obj_dataSnapshot : dataSnapshot.getChildren()) {
                    Atividade atividade = obj_dataSnapshot.getValue(Atividade.class);
                    listaAtividade.add(atividade);
                }
                ArrayAdapter atividadeAdapter =
                        new ArrayAdapter<Atividade>(MainActivity.this, android.R.layout.simple_list_item_1, listaAtividade);
                livAtividade.setAdapter(atividadeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void pesquisar(final int codigo) {
        databaseReference.child("atividade").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaAtividade.clear();
                for (DataSnapshot obj_dataSnapshot : dataSnapshot.getChildren()) {
                    Atividade atividade = obj_dataSnapshot.getValue(Atividade.class);
                    if (atividade.getCodigo()==codigo) {
                        listaAtividade.add(atividade);
                        break;
                    }
                }
                ArrayAdapter atividadeAdapter =
                        new ArrayAdapter<Atividade>(MainActivity.this, android.R.layout.simple_list_item_1, listaAtividade);
                livAtividade.setAdapter(atividadeAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

}
