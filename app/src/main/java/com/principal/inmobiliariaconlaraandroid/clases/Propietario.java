package com.principal.inmobiliariaconlaraandroid.clases;

public class Propietario {
    private int idPropietario;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String clave;
    private String telefono;

    public Propietario(int idPropietario,String dni,String nombre,String apellido,String email,String clave,String telefono) {
        this.idPropietario = idPropietario;
        this.dni=dni;
        this.nombre=nombre;
        this.apellido=apellido;
        this.email =email;
        this.clave =clave;
        this.telefono=telefono;


    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
