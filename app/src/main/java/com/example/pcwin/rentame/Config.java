package com.example.pcwin.rentame;



    public class Config {

        //WEB ADRESS
        public static final String URL_LOGIN_USER_RESULT_WEB= "https://knotfoldbase.000webhostapp.com/login_user_result.php?NombreCliente=";
        public static final String URL_ADD_COMMENT_WEB= "https://knotfoldbase.000webhostapp.com/add_comment.php";
        public static final String URL_ADD_CLIENTE_WEB= "https://knotfoldbase.000webhostapp.com/add_cliente.php";
        public static final String URL_ADD_USER_WEB= "https://rentame.000webhostapp.com/add_arrendatario.php";
        public static final String URL_UPDATE_USER_IDDEPTO = "https://rentame.000webhostapp.com/updateIDDARR.php";
        public static final String URL_UPDATE_DEPTO_DISPONIBILIDAD = "https://rentame.000webhostapp.com/updatedisponible.php";
        public static final String URL_ADD_NoCuenta= "https://rentame.000webhostapp.com/add_amp.php";
        public static final String URL_ADD_Contrato= "https://rentame.000webhostapp.com/add_contrato.php";
        public static final String URL_ADD_Pagor = "https://rentame.000webhostapp.com/add_pagorenta.php";
        public static final String URL_ADD_mensaje = "https://rentame.000webhostapp.com/add_mensaje.php";
        public static final String URL_UPDate_User = "https://rentame.000webhostapp.com/updateusuario.php";
        public static final String URL_ADD_PROM_WEB= "https://rentame.000webhostapp.com/add_promo.php ";

        //Keys that will be used to send the request to php scripts

        //Tabla Cliente
        public static String KEY_EMP_IDArrendatario = "IDArrendatario";
        public static String KEY_EMP_IDDepto = "IDDepto";
        public static final String KEY_EMP_NombreUsuario = "Nombre";
        public static final String KEY_EMP_ApellidoP = "ApellidoP";
        public static final String KEY_EMP_ApellidoM = "ApellidoM";
        public static final String KEY_EMP_Tcel= "Tcelular";
        public static final String KEY_EMP_TCasa = "TelCasa";
        public static final String KEY_EMP_TOficina = "TelOficina";
        public static final String KEY_EMP_FechNac = "FechNac";
        public static final String KEY_EMP_Email  = "Email";
        public static final String KEY_EMP_ContrasenaUsuario = "contrasena";
        public static final String KEY_EMP_Sexo  = "Sexo";
        public static final String KEY_EMP_IDENTICIACION = "Identificacion";
        //Tabla ArrenMpago
        public static String KEY_EMP_Nocuenta = "NumeroCuenta";
        public static String KEY_EMP_NombreP = "NomPropieCuenta";
        public static String KEY_EMP_Codseg = "Codseg";
        public static String KEY_EMP_Fecha_ArrenMpago = "Fecha";
        //Tabla pagoRenta
        public static String KEY_EMP_Monto = "Monto";
        public static String KEY_EMP_Fecha_PagoR= "Fecha";
        public static String KEY_EMP_ID_AMP = "IDamp";
        //Tabla contrato
        public static String KEY_EMP_FINICIO = "Finicio";
        public static String KEY_EMP_FFINAL = "Ffinal";
        public static String KEY_EMP_PAGOM = "Pagom";
        public static String KEY_EMP_SumTotal = "SumTotal";
        //Tabla Mensaje
        public static String KEY_EMP_MENSAJE = "Mensaje";
        public static String KEY_EMP_FECHA_MENSAJE= "fecha";
        public static String KEY_EMP_TIPO_MENSAJE = "Tipo";






        //PASS RES
        public static final String EMP_IdRestaurante = "IdRestaurane";
        public static final String EMP_NombreRes = "NombreRes";
        public static final String EMP_UbicacionRes = "UbicacionRes";
        public static final String EMP_HorarioRes = "HorarioRes";
        public static final String EMP_AforoRes = "AforoRes";
        public static final String EMP_TipoRes = "TipoRes";
        public static final String EMP_ContactoRes = "ContactoRes";
        public static final String EMP_image= "image";
    }

