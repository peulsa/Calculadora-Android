package com.example.lacalculadoraof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private EditText etNumero;
    private TextView tvResultado;
    private Button btnSumar, btnRestar, btnMultiplicar, btnDividir, btnIgual, btnC, btnHistorial;

    private ArrayList<Double> numeros = new ArrayList<>();
    private ArrayList<String> operaciones = new ArrayList<>();
    private ArrayList<String> historial = new ArrayList<>(); // Lista para almacenar el historial

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumero = findViewById(R.id.etNumero);
        tvResultado = findViewById(R.id.tvResultado);
        btnSumar = findViewById(R.id.btnSumar);
        btnRestar = findViewById(R.id.btnRestar);
        btnMultiplicar = findViewById(R.id.btnMultiplicar);
        btnDividir = findViewById(R.id.btnDividir);
        btnIgual = findViewById(R.id.btnIgual);
        btnC = findViewById(R.id.btnC);
        btnHistorial = findViewById(R.id.btnHistorial);

        btnSumar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardarNumeroYOperacion("+");
            }
        });

        btnRestar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardarNumeroYOperacion("-");
            }
        });

        btnMultiplicar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardarNumeroYOperacion("*");
            }
        });

        btnDividir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardarNumeroYOperacion("/");
            }
        });

        btnIgual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calcularResultado();
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                limpiar();
            }
        });

        btnHistorial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mostrarHistorial();
            }
        });
    }

    private void guardarNumeroYOperacion(String op) {
        String numeroIngresado = etNumero.getText().toString();
        if (!numeroIngresado.isEmpty()) {
            double numero = Double.parseDouble(numeroIngresado);
            numeros.add(numero);
            operaciones.add(op);
            etNumero.setText("");
        } else {
            Toast.makeText(this, "Por favor ingrese un número", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarHistorial() {
        Intent intent = new Intent(this, historialFuncion.class);
        intent.putStringArrayListExtra("historial", historial);
        startActivity(intent);
    }

    private void limpiar() {
        numeros.clear();
        operaciones.clear();
        etNumero.setText("");
        tvResultado.setText("Resultado:");
    }

    private void calcularResultado() {
        String numeroIngresado = etNumero.getText().toString();
        if (!numeroIngresado.isEmpty()) {
            double numero = Double.parseDouble(numeroIngresado);
            numeros.add(numero);

            double resultado = numeros.get(0);
            StringBuilder operacionCompleta = new StringBuilder(String.valueOf(numeros.get(0)));

            for (int i = 0; i < operaciones.size(); i++) {
                String operacion = operaciones.get(i);
                double numeroSiguiente = numeros.get(i + 1);

                switch (operacion) {
                    case "+":
                        resultado += numeroSiguiente;
                        break;
                    case "-":
                        resultado -= numeroSiguiente;
                        break;
                    case "*":
                        resultado *= numeroSiguiente;
                        break;
                    case "/":
                        if (numeroSiguiente != 0) {
                            resultado /= numeroSiguiente;
                        } else {
                            Toast.makeText(this, "No se puede dividir por cero", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                }
                operacionCompleta.append(" ").append(operacion).append(" ").append(numeroSiguiente);
            }

            // Almacenar en el historial

            if (resultado == (int) resultado) {
                tvResultado.setText("Resultado: " + (int) resultado);
            } else {
                tvResultado.setText("Resultado: " + resultado);
            }

            numeros.clear();
            operaciones.clear();
            operacionCompleta.append(" = ").append(resultado);
            historial.add(operacionCompleta.toString());
            etNumero.setText("");
        } else {
            Toast.makeText(this, "Por favor ingrese un número", Toast.LENGTH_SHORT).show();
        }
    }
}
