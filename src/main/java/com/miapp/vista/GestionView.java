package com.miapp.vista;

import com.miapp.controlador.GestionController;
import com.miapp.modelo.Curso;
import com.miapp.modelo.Estudiante;
import com.miapp.modelo.Profesor;
import com.miapp.utilidades.EstadoMatricula;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionView extends JFrame {

    private static final int ANCHO_VENTANA = 700;
    private static final int ALTO_VENTANA = 500;

    private static final String[] COLUMNAS_CURSO = {"Código", "Créditos", "Estudiantes inscritos"};
    private static final String[] COLUMNAS_PROFESOR = {"ID", "Nombre", "Apellido", "Salario base"};
    private static final String[] COLUMNAS_ESTADO = {"ID", "Nombre", "Apellido", "Carrera", "Estado"};

    private GestionController controlador;

    // Cursos
    private JTextField txtCodigoCurso;
    private JTextField txtCreditosCurso;
    private DefaultTableModel modeloTablaCursos;

    // Profesores
    private JTextField txtIdProfesor;
    private JTextField txtNombreProfesor;
    private JTextField txtApellidoProfesor;
    private JTextField txtSalarioProfesor;
    private DefaultTableModel modeloTablaProfesores;

    // Matrícula
    private JTextField txtIdEstudianteMatricula;
    private JTextField txtCodigoCursoMatricula;
    private JLabel lblEstadoMatricula;
    
    // Estado de matrícula
    private JTextField txtIdEstudianteEstado;
    private JComboBox<EstadoMatricula> cmbNuevoEstado;
    private JComboBox<EstadoMatricula> cmbBuscarEstado;
    private DefaultTableModel modeloTablaEstado;

    public GestionView() {
        setTitle("Gestión Académica — Cursos, Profesores y Matrícula");
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Cursos", crearPanelCursos());
        tabs.addTab("Profesores", crearPanelProfesores());
        tabs.addTab("Matrícula", crearPanelMatricula());
        tabs.addTab("Estado Matrícula", crearPanelEstado());    

        add(tabs);
    }

    public void setControlador(GestionController controlador) {
        this.controlador = controlador;
        refrescarCursos();
        refrescarProfesores();
    }

    // ── Panel Cursos ──────────────────────────────────────────────────────────

    private JPanel crearPanelCursos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel form = new JPanel();
        form.add(new JLabel("Código:"));
        txtCodigoCurso = new JTextField(8);
        form.add(txtCodigoCurso);
        form.add(new JLabel("Créditos:"));
        txtCreditosCurso = new JTextField(4);
        form.add(txtCreditosCurso);

        JButton btnAgregar = new JButton("Agregar curso");
        btnAgregar.addActionListener(e -> agregarCurso());
        form.add(btnAgregar);

        modeloTablaCursos = new DefaultTableModel(COLUMNAS_CURSO, 0);
        JTable tabla = new JTable(modeloTablaCursos);

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private void agregarCurso() {
        try {
            String codigo = txtCodigoCurso.getText().trim();
            int creditos = Integer.parseInt(txtCreditosCurso.getText().trim());
            if (controlador.agregarCurso(codigo, creditos)) {
                txtCodigoCurso.setText("");
                txtCreditosCurso.setText("");
                refrescarCursos();
            }
        } catch (NumberFormatException ex) {
            mostrarError("Los créditos deben ser un número entero.");
        }
    }

    private void refrescarCursos() {
        modeloTablaCursos.setRowCount(0);
        for (Curso c : controlador.obtenerCursos()) {
            modeloTablaCursos.addRow(new Object[]{
                    c.getCodigo(), c.getCreditos(), c.getEstudiantes().size()
            });
        }
    }

    // ── Panel Profesores ──────────────────────────────────────────────────────

    private JPanel crearPanelProfesores() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel form = new JPanel();
        form.add(new JLabel("ID:"));
        txtIdProfesor = new JTextField(4);
        form.add(txtIdProfesor);
        form.add(new JLabel("Nombre:"));
        txtNombreProfesor = new JTextField(8);
        form.add(txtNombreProfesor);
        form.add(new JLabel("Apellido:"));
        txtApellidoProfesor = new JTextField(8);
        form.add(txtApellidoProfesor);
        form.add(new JLabel("Salario base:"));
        txtSalarioProfesor = new JTextField(8);
        form.add(txtSalarioProfesor);

        JButton btnAgregar = new JButton("Agregar profesor");
        btnAgregar.addActionListener(e -> agregarProfesor());
        form.add(btnAgregar);

        modeloTablaProfesores = new DefaultTableModel(COLUMNAS_PROFESOR, 0);
        JTable tabla = new JTable(modeloTablaProfesores);

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private void agregarProfesor() {
        try {
            int id = Integer.parseInt(txtIdProfesor.getText().trim());
            String nombre = txtNombreProfesor.getText().trim();
            String apellido = txtApellidoProfesor.getText().trim();
            double salario = Double.parseDouble(txtSalarioProfesor.getText().trim());

            if (controlador.agregarProfesor(nombre, apellido, id, salario)) {
                txtIdProfesor.setText("");
                txtNombreProfesor.setText("");
                txtApellidoProfesor.setText("");
                txtSalarioProfesor.setText("");
                refrescarProfesores();
            }
        } catch (NumberFormatException ex) {
            mostrarError("ID y salario deben ser numéricos.");
        }
    }

    private void refrescarProfesores() {
        modeloTablaProfesores.setRowCount(0);
        for (Profesor p : controlador.obtenerProfesores()) {
            modeloTablaProfesores.addRow(new Object[]{
                    p.getId(), p.getNombre(), p.getApellido(),
                    String.format("%.2f", p.getSalarioBase())
            });
        }
    }

    // ── Panel Matrícula ───────────────────────────────────────────────────────

    private JPanel crearPanelMatricula() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel form = new JPanel();
        form.add(new JLabel("ID Estudiante:"));
        txtIdEstudianteMatricula = new JTextField(4);
        form.add(txtIdEstudianteMatricula);
        form.add(new JLabel("Código Curso:"));
        txtCodigoCursoMatricula = new JTextField(8);
        form.add(txtCodigoCursoMatricula);

        JButton btnMatricular = new JButton("Inscribir");
        btnMatricular.addActionListener(e -> matricular());
        form.add(btnMatricular);

        lblEstadoMatricula = new JLabel(" ");

        panel.add(form, BorderLayout.NORTH);
        panel.add(lblEstadoMatricula, BorderLayout.CENTER);
        return panel;
    }

    private void matricular() {
        try {
            int idEstudiante = Integer.parseInt(txtIdEstudianteMatricula.getText().trim());
            String codigoCurso = txtCodigoCursoMatricula.getText().trim();
            controlador.matricular(idEstudiante, codigoCurso);
            refrescarCursos();
        } catch (NumberFormatException ex) {
            mostrarError("El ID del estudiante debe ser numérico.");
        }
    }

    
    // ── Panel Estado Matrícula ───────────────────────────────────────────────

private JPanel crearPanelEstado() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));

    // Fila para cambiar el estado de un estudiante puntual
    JPanel formCambiar = new JPanel();
    formCambiar.add(new JLabel("ID Estudiante:"));
    txtIdEstudianteEstado = new JTextField(4);
    formCambiar.add(txtIdEstudianteEstado);
    formCambiar.add(new JLabel("Nuevo estado:"));
    cmbNuevoEstado = new JComboBox<>(EstadoMatricula.values());
    formCambiar.add(cmbNuevoEstado);
    JButton btnCambiar = new JButton("Cambiar estado");
    btnCambiar.addActionListener(e -> cambiarEstado());
    formCambiar.add(btnCambiar);

    // Fila para buscar estudiantes por estado
    JPanel formBuscar = new JPanel();
    formBuscar.add(new JLabel("Buscar por estado:"));
    cmbBuscarEstado = new JComboBox<>(EstadoMatricula.values());
    formBuscar.add(cmbBuscarEstado);
    JButton btnBuscar = new JButton("Buscar");
    btnBuscar.addActionListener(e -> buscarPorEstado());
    formBuscar.add(btnBuscar);

    JPanel formularios = new JPanel(new GridLayout(2, 1));
    formularios.add(formCambiar);
    formularios.add(formBuscar);

    modeloTablaEstado = new DefaultTableModel(COLUMNAS_ESTADO, 0);
    JTable tabla = new JTable(modeloTablaEstado);

    panel.add(formularios, BorderLayout.NORTH);
    panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
    return panel;
}

private void cambiarEstado() {
    try {
        int idEstudiante = Integer.parseInt(txtIdEstudianteEstado.getText().trim());
        EstadoMatricula nuevoEstado = (EstadoMatricula) cmbNuevoEstado.getSelectedItem();
        if (controlador.cambiarEstadoMatricula(idEstudiante, nuevoEstado)) {
            txtIdEstudianteEstado.setText("");
            buscarPorEstado();
        }
    } catch (NumberFormatException ex) {
        mostrarError("El ID del estudiante debe ser numérico.");
    }
}

private void buscarPorEstado() {
    EstadoMatricula estado = (EstadoMatricula) cmbBuscarEstado.getSelectedItem();
    List<Estudiante> resultados = controlador.buscarPorEstado(estado);

    modeloTablaEstado.setRowCount(0);
    for (Estudiante e : resultados) {
        modeloTablaEstado.addRow(new Object[]{
                e.getId(), e.getNombre(), e.getApellido(), e.getCarrera(), e.getEstado()
        });
    }
}
    // ── Métodos que el Controlador llama para mostrar resultados ────────────────

    public void mostrarMensaje(String mensaje) {
        lblEstadoMatricula.setText(mensaje);
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        lblEstadoMatricula.setText(mensaje);
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
