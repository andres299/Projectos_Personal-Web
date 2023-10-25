import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import dao.*;
import dao.ClientDAOImpl;
import model.*;
import model.Detall_Compra;

public class Main {
    private static final int MAX_ATTEMPTS = 3;
    private static List<Detall_Compra> detallesCompra = new ArrayList<>();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("***********************");
        System.out.println("**    Limbo app      **");
        System.out.println("***********************");
        while (true) {
            int op = triaOpcio();
            switch (op) {
                case 1:
                    String loginResult = login(scanner);
                    if (loginResult.equals("SUCCESS")) {
                        System.out.println("Inicio de sesión exitoso. ¡Bienvenido!");
                    } else if (loginResult.equals("INVALID_USERNAME")) {
                        System.out.println("Inicio de sesión fallido. El usuario ingresado no es válido.");
                    } else if (loginResult.equals("INVALID_PASSWORD")) {
                        System.out.println("Inicio de sesión fallido. La contraseña ingresada no es válida.");
                    } else if (loginResult.equals("MAX_ATTEMPTS_EXCEEDED")) {
                        System.out.println("Número máximo de intentos alcanzado. Cerrando aplicación.");
                        System.exit(0);
                    }
                    break;
                case 2:
                    registre();
                    break;
                case 3:
                    System.out.println("Ahora mismo estamos teniendo unos problemas de mantenimiento,por favor " +
                            "intentelo mas tarde!!!");
                    break;
                case 4:
                    // Opción 4: Finalizar el programa
                    System.out.println("Finalizando el programa...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcio no valida");
            }
        }
    }

    private static int triaOpcio() {
        System.out.println("1-Login");
        System.out.println("2-Registrarse");
        System.out.println("3-Ajuda");
        System.out.println("4-Sortir");
        System.out.println("Esculli una opcio");
        Scanner scanner = new Scanner(System.in);
        String op = scanner.nextLine();
        return Integer.parseInt(op);
    }

    private static void registre() {
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Donam un email");
        String email = scanner1.nextLine();
        System.out.println("Se ha registrado un usuario con email: " + email);
        System.out.println("Donam un nom");
        String nom = scanner1.nextLine();

        System.out.println("Donam un cognom1");
        String cognom1 = scanner1.nextLine();

        System.out.println("Donam un cognom2");
        String cognom2 = scanner1.nextLine();

        System.out.println("Donam un Usuario");
        String username = scanner.nextLine();

        System.out.println("Donam un password");
        String password = scanner1.nextLine();

        ClientDAO clientDAO = new ClientDAOImpl();
        clientDAO.registre(new Client(email, nom, cognom1, cognom2, username, password));
    }

    public static String login(Scanner scanner) {
        int attempts = 0;
        ClientDAO clientDAO = new ClientDAOImpl();

        while (attempts < MAX_ATTEMPTS) {
            System.out.println("Ingrese su nombre de usuario:");
            String username = scanner.nextLine();
            System.out.println("Ingrese su contraseña:");
            String password = scanner.nextLine();

            Client client = clientDAO.login(username, password);
            if (client != null) {
                mostrarMenuUsuario(client.getUsername());
                return "SUCCESS"; // Inicio de sesión exitoso
            } else {
                attempts++;
                if (!clientDAO.existeUsuario(username)) {
                    System.out.println("Usuario no válido.");
                } else {
                    System.out.println("Contraseña no válida.");
                }
            }
        }

        return "MAX_ATTEMPTS_EXCEEDED";
    }

    private static void mostrarMenuUsuario(String username) {
        Detall_CompraDAO detall_compraDAO = new Detall_CompraDAOImpl();
        ClientDAO clientDAO = new ClientDAOImpl();
        Client client = clientDAO.datosUsuarioPersonal(username);
        Scanner scanner = new Scanner(System.in);
        System.out.println("***********************");
        System.out.println("**    Opciones      **");
        System.out.println("Bienvenido Usuario: " + client.getNom() + " " + client.getCognom1() + " " + username);
        System.out.println("Cistella: " + detall_compraDAO.totalPVP());
        System.out.println("***********************");

        System.out.println("5-Cercar productes");
        System.out.println("6-Veure Cistella");
        System.out.println("7-Dades Personals");
        System.out.println("8-Ajuda");
        System.out.println("9-Sortir");
        System.out.println("-----------Productes Sugerits----------");
        mostrarProductosSugeridos();
        System.out.println("---------------------------------------");
        System.out.println("(0-4) Productos Sugeridos");
        System.out.println("Esculli una opcio");

        String op = scanner.nextLine();
        triaOpcioUsuari(op, username);
    }

    private static void triaOpcioUsuari(String op, String username) {
        while (true) {
            int opcion = Integer.parseInt(op);
            switch (opcion) {
                case 5:
                    System.out.println("***********************");
                    System.out.println("**      Cerca        **");
                    System.out.println("***********************");
                    System.out.println("Filtrar productos por:");
                    System.out.println("1. Nombre");
                    System.out.println("2. Descripción");
                    System.out.println("3. Marca");
                    System.out.println("4. Categoría");
                    System.out.println("5. Volver al menú principal");
                    System.out.println("Seleccione una opción:");
                    String filtroOp = scanner.nextLine();

                    if (filtroOp.equals("5")) {
                        mostrarMenuUsuario(username);
                    }

                    ProducteDAO producteDAO = new ProducteDAOImpl();
                    List<Producte> productosFiltrados = filtrarProductos(producteDAO, filtroOp);

                    if (productosFiltrados.isEmpty()) {
                        System.out.println("No se encontraron productos que coincidan con los criterios de búsqueda.");
                    } else {
                        mostrarProductos(productosFiltrados);
                        seleccionarYGuardarCompra(productosFiltrados);
                    }
                    break;
                case 6:
                    veureCistella(username);
                    break;
                case 7:
                    dadesPersonals(username);
                    return;
                case 8:
                    System.out.println("Ahora mismo estamos teniendo unos problemas de mantenimiento,por favor " +
                            "intentelo mas tarde!!!");
                    return;
                case 9:
                    System.out.println("Finalizando el programa...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcio no valida");
                    System.out.println("Esculli una opcio");
                    op = scanner.nextLine();
            }
        }
    }

    private static void mostrarProductosSugeridos() {
        ProducteDAOImpl producteDAO = new ProducteDAOImpl();
        List<Producte> productosSugeridos = producteDAO.obtenerProductosSugeridos();
        System.out.println("Productos sugeridos:");

        // Imprimir encabezados de las columnas
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-20s | %-30s | %-10s | %-5s | %-20s | %-20s | %-10s | %-10s%n",
                "ID", "Nombre", "Descripción", "Precio", "IVA", "Marca", "Unidad de medida", "Peso", "Categoría");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");

        // Imprimir filas de productos
        for (Producte producto : productosSugeridos) {
            System.out.printf("%-5s | %-20s | %-30s | %-10s | %-5s | %-20s | %-20s | %-10s | %-10s%n",
                    producto.getId(), producto.getNom(), producto.getDescripcio(), producto.getPvp(),
                    producto.getIva(), producto.getMarca(), producto.getUnitat_mesura(), producto.getPes(),
                    producto.getCategoria());
        }
    }

    private static List<Producte> filtrarProductos(ProducteDAO producteDAO, String filtroOp) {
        if (filtroOp.equals("4")) {
            System.out.println("Aqui estan todas las opciones:");
            CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
            List<Categoria> categorias = categoriaDAO.mostrarTodasCategorias();
            System.out.println("Opciones de categoría:");
            for (Categoria categoria : categorias) {
                System.out.println(categoria.getId() + ". " + categoria.getNom() + " " + categoria.getDescripcio());
            }
            System.out.println("Seleccione una categoría:");
            String categoriaId = scanner.nextLine();
            if (categoriaId.isEmpty()) {
                return producteDAO.mostrarTodosProductos();
            } else {
                return producteDAO.filtrarPorCategoria(categoriaId);
            }

        } else {
            System.out.println("Ingrese el valor del filtro:");
            String valorFiltro = scanner.nextLine();
            if (valorFiltro.isEmpty()) {
                return producteDAO.mostrarTodosProductos();
            } else {
                switch (filtroOp) {
                    case "1":
                        return producteDAO.filtrarPorNombre(valorFiltro);
                    case "2":
                        return producteDAO.filtrarPorDescripcion(valorFiltro);
                    case "3":
                        return producteDAO.filtrarPorMarca(valorFiltro);
                    default:
                        System.out.println("Opción de filtro no válida");
                        return new ArrayList<>(); // Devuelve una lista vacía en caso de opción inválida
                }
            }
        }
    }

    private static void mostrarProductos(List<Producte> productosFiltrados) {
        int cantidadProductos = productosFiltrados.size();
        System.out.println("Se han encontrado " + cantidadProductos + " productos:");
        for (int i = 0; i < cantidadProductos; i++) {
            Producte producte = productosFiltrados.get(i);
            System.out.println("[" + i + "]" + producte.getNom() + " | " + producte.getDescripcio() + " | "
                    + producte.getCategoria() + " | " + producte.getMarca() + " | " + producte.getPvp());
        }
    }

    private static void seleccionarYGuardarCompra(List<Producte> productosFiltrados) {
        System.out.println("Seleccione el número del producto para comprar:");
        int numeroProducto = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de leer un entero
        if (numeroProducto < 0 || numeroProducto >= productosFiltrados.size()) {
            System.out.println("El número de producto seleccionado no es válido.");
            return;
        }

        Producte productoSeleccionado = productosFiltrados.get(numeroProducto);

        System.out.println("Ingrese la cantidad deseada:");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        // Generar un identificador aleatorio con longitud 20
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 20; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        String id_transaccio = sb.toString();
        Date fechaActual = new Date();
        java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());
        Compra compra = new Compra(id_transaccio, fechaSQL);
        CompraDAO compraDAO = new CompraDAOImpl();

        int compraId = compraDAO.guardarCompra(compra); // Obtener la id generada de la compra
        int productoId = productoSeleccionado.getId();


        Detall_Compra detallCompra = new Detall_Compra(compraId, productoId, productoSeleccionado.getPvp(), cantidad);
        Detall_CompraDAO detall_compraDAO = new Detall_CompraDAOImpl();
        detall_compraDAO.guardarDetall_Compra(detallCompra);

        System.out.println("Compra registrada con éxito:");
        System.out.println("Producto: " + productoSeleccionado.getNom());
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Fecha: " + fechaSQL);
    }

    private static void veureCistella(String username) {
        Detall_CompraDAO detall_compraDAO = new Detall_CompraDAOImpl();
        List<Detall_Compra> detallesCompra = detall_compraDAO.obtenerDetallesCompra();
        System.out.println("***********************");
        System.out.println("**     Cistella      **");
        System.out.println("***********************");
        System.out.println("-----------------------");
        int contador = 0;
        double totalCistella = 0.0;
        for (Detall_Compra detalle : detallesCompra) {
            int producteId = detalle.getProducte_id();
            // Obtén el producto correspondiente al ID del detalle de compra
            ProducteDAO producteDAO = new ProducteDAOImpl();
            Producte producte = producteDAO.obtenerProductoPorId(producteId);

            // Muestra el nombre del producto, el precio y las unidades del producto
            System.out.println(contador + " " + producte.getNom() + " " + producte.getPvp() + " " + detalle.getUnitats_producte() + " unitats");
            double subtotal = producte.getPvp() * detalle.getUnitats_producte();
            totalCistella += subtotal;
            contador++;
        }
        System.out.println("-----------------------");
        System.out.println("Total cistella: " + totalCistella);
        System.out.println("-----------------------");
        System.out.println("1. Eliminar Producto");
        System.out.println("2. Pagar");
        System.out.println("3. Salir de la Cistella");
        System.out.println("Seleccione una opción:");
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                eliminarProducte(detallesCompra);
                break;
            case 2:
                mostrarPagament();
                break;
            case 3:
                mostrarMenuUsuario(username);
                return;
            default:
                System.out.println("Opción inválida. Seleccione una opción válida.");
                break;
        }
    }

    private static void eliminarProducte(List<Detall_Compra> detallesCompra) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione el número del producto a eliminar: " + "(El numero que esta al lado de la " +
                "lista de producto)"); //El numero de la izquierda
        int productoEliminar = scanner.nextInt();
        if (productoEliminar >= 0 && productoEliminar < detallesCompra.size()) {
            Detall_Compra detalleEliminar = detallesCompra.get(productoEliminar);
            Detall_CompraDAO detall_compraDAO = new Detall_CompraDAOImpl();
            detall_compraDAO.eliminarDetallCompra(detalleEliminar);
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Número de producto inválido.");
        }
    }

    private static void mostrarPagament() {
        Detall_CompraDAO detall_compraDAO = new Detall_CompraDAOImpl();
        List<Detall_Compra> detallesCompra = detall_compraDAO.obtenerDetallesCompra();
        System.out.println("***********************");
        System.out.println("**     Pagament      **");
        System.out.println("***********************");
        System.out.println("-----------------------");
        int contador = 0;
        double totalCistella = 0.0;
        for (Detall_Compra detalle : detallesCompra) {
            int producteId = detalle.getProducte_id();

            // Obtén el producto correspondiente al ID del detalle de compra
            ProducteDAO producteDAO = new ProducteDAOImpl();
            Producte producte = producteDAO.obtenerProductoPorId(producteId);

            // Muestra el nombre del producto, el precio y las unidades del producto
            System.out.println(contador + " " + producte.getNom() + " " + producte.getPvp() + " " + detalle.getUnitats_producte() + " unitats");
            double subtotal = producte.getPvp() * detalle.getUnitats_producte();
            totalCistella += subtotal;
            contador++;
        }
        System.out.println("-----------------------");
        System.out.println("Total cistella: " + totalCistella);
        System.out.println("-----------------------");
        System.out.println("Targetas Disponibles");
        TargetaDAO targetaDAO = new TargetaDAOImpl();
        List<Targeta> mostrarTargetas = targetaDAO.mostrarTargetas();
        for (int i = 0; i < mostrarTargetas.size(); i++) {
            Targeta targeta = mostrarTargetas.get(i);
            System.out.println("[" + i + "] " + targeta.getNumero() + " " + targeta.getTipus() + " " + targeta.getData_caducitat());
        }
        System.out.println("1. Afegir Targeta");
        System.out.println("2. Elejir targeta Disponible");
        System.out.println("3. Salir");
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                afegirTargeta();
            case 2:
                System.out.println("Seleccione una tarjeta disponible:");
                int opcionTarjeta = scanner.nextInt();
                scanner.nextLine();
                Targeta targetaSeleccionada = mostrarTargetas.get(opcionTarjeta);
                int client_id = verificarCliente();
                elegirTargetaExistente(client_id, targetaSeleccionada);
                return;
            case 3:
                return;
            default:
                System.out.println("Opción inválida. Seleccione una opción válida.");
                break;
        }
    }

    private static void afegirTargeta() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el tipo de tarjeta:");
        String tipus = scanner.nextLine();

        System.out.println("Ingrese el número de tarjeta:");
        int numero = scanner.nextInt();

        System.out.println("Ingrese la fecha de caducidad de la tarjeta (en formato dd/MM/yyyy):");
        String fechaCaducidad = scanner.next();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data_caducitat;
        try {
            data_caducitat = formatter.parse(fechaCaducidad);
        } catch (ParseException e) {
            System.out.println("Fecha de caducidad inválida. Inténtelo de nuevo.");
            return;
        }

        System.out.println("Ingrese el código de seguridad de la tarjeta:");
        int codi_seguretat = scanner.nextInt();

        int client_id = verificarCliente();

        TargetaDAO targetaDAO = new TargetaDAOImpl();
        targetaDAO.registre(tipus, numero, data_caducitat, codi_seguretat, client_id);
        Targeta targetaSeleccionada = new Targeta(numero, tipus, data_caducitat);
        elegirTargetaExistente(client_id, targetaSeleccionada);
    }

    private static int verificarCliente() {
        ClientDAO clienteDAO = new ClientDAOImpl(); // Instancia del DAO
        List<Client> clients = clienteDAO.getClientList();

        // Mostrar número de clientes y sus nombres de usuario
        for (Client client : clients) {
            System.out.println(client.getNumero_client() + " " + client.getUsername());
            System.out.println("-------------------------");
        }

        System.out.println("Ingrese el número de cliente:");
        int clientNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese la contraseña:");
        String password = scanner.nextLine();

        Client client = clienteDAO.loginDNI(clientNumber, password);
        if (client != null) {
            System.out.println("Contraseña correcta. Acceso permitido.");
            return clientNumber; // Devolver el número de cliente verificado
        } else {
            // Contraseña incorrecta o número de cliente inválido
            System.out.println("Contraseña incorrecta o número de cliente inválido. Acceso denegado.");
            return -1; // Devolver un valor indicando que la verificación falló
        }
    }

    private static void elegirTargetaExistente(int client_Id, Targeta targetaSeleccionada) {
        AdrecaDAO adrecaDAO = new AdrecaDAOImpl();
        System.out.println("Adreca Disponibles:");
        List<Adreca> mostrarAdreça = adrecaDAO.mostrarAdreça();
        for (int i = 0; i < mostrarAdreça.size(); i++) {
            Adreca adreca = mostrarAdreça.get(i);
            System.out.println("[" + i + "]" + " " + adreca.getCarrer() + " " + adreca.getNumero() + " " + adreca.getPorta());
        }
        System.out.println("1. Afegir Adreca");
        System.out.println("2. Elejir Adreca Disponible");
        System.out.println("3. Salir");
        System.out.println("Seleccione una Adreça disponible:");
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                afegirAdreca(client_Id);
            case 2:
                System.out.println("Seleccione una adreca disponible:");
                int opcionAdreca = scanner.nextInt();
                scanner.nextLine();
                Adreca adrecaSelecciona = mostrarAdreça.get(opcionAdreca);
                confirmarAdreçaCompra(targetaSeleccionada, adrecaSelecciona, client_Id);
                return;
            case 3:
                return;
            default:
                System.out.println("Opción inválida. Seleccione una opción válida.");
                break;
        }
    }

    private static void afegirAdreca(int client_id) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre de la calle:");
        String carrer = scanner.nextLine();

        System.out.println("Ingrese el número:");
        String numero = scanner.nextLine();

        System.out.println("Ingrese el piso (opcional):");
        int pis = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese la puerta (opcional):");
        String porta = scanner.nextLine();

        System.out.println("Ingrese el código postal:");
        String CP = scanner.nextLine();

        System.out.println("Ingrese el ID de la ciudad:");
        int ciutat_id = scanner.nextInt();
        scanner.nextLine();

        AdrecaDAO adrecaDAO = new AdrecaDAOImpl();
        boolean registroExitoso = adrecaDAO.registre(new Adreca(client_id, carrer, numero, pis, porta, CP, ciutat_id));

        if (registroExitoso) {
            System.out.println("Se ha guardado la dirección correctamente.");
        } else {
            System.out.println("No se ha podido guardar la dirección.");
        }
    }

    private static void confirmarAdreçaCompra(Targeta targetaSeleccionada, Adreca adrecaSelecciona, int Client) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ens fara el seguent pagament");
        ClientDAO clientDAO = new ClientDAOImpl();
        Client client = clientDAO.datosUsuario(Client);
        if (client != null) {
            System.out.println(client.getNom() + " " + client.getCognom1() + " " + client.getCognom2() + " " + client.getEmail());
        } else {
            System.out.println("El cliente no existe o no se encontraron datos.");
        }
        Detall_CompraDAO detall_compraDAO = new Detall_CompraDAOImpl();
        List<Detall_Compra> detallesCompra = detall_compraDAO.obtenerDetallesCompra();
        int contador = 0;
        double totalCistella = 0.0;
        for (Detall_Compra detalle : detallesCompra) {
            int producteId = detalle.getProducte_id();

            // Obtén el producto correspondiente al ID del detalle de compra
            ProducteDAO producteDAO = new ProducteDAOImpl();
            Producte producte = producteDAO.obtenerProductoPorId(producteId);

            // Muestra el nombre del producto, el precio y las unidades del producto
            System.out.println(contador + " " + producte.getNom() + " " + producte.getPvp() + " " + detalle.getUnitats_producte() + " unitats");
            double subtotal = producte.getPvp() * detalle.getUnitats_producte();
            totalCistella += subtotal;
            contador++;
        }
        System.out.println("-----------------------");
        System.out.println("Total cistella: " + totalCistella);
        System.out.println("-----------------------");
        System.out.println("Amb la targeta");
        System.out.println(targetaSeleccionada);
        System.out.println("Amb la adreca");
        System.out.println(adrecaSelecciona);
        System.out.println("Confrimar Compra (Si/No)");
        String opcion = scanner.nextLine();
        if (opcion.equals("Si")) {
            assert client != null;
            System.out.println("COMPRA REALIZADA CORRECTAMENT: S'ha enviat la factura a " + client.getEmail());
        } else {
            System.out.println("No se ha confirmado la compra");
        }
    }

    private static void dadesPersonals(String username) {
        ClientDAO clientDAO = new ClientDAOImpl();
        Client client = clientDAO.datosUsuarioPersonal(username);
        Scanner scanner = new Scanner(System.in);
        System.out.println("*******************************");
        System.out.println("**      Dades Personals       **");
        System.out.println("Usuari: " + client.getNom() + " " + client.getCognom1() + " " + username);
        System.out.println("********************************");
        System.out.println("1. Settings");
        System.out.println("2. Compres realitzades");
        System.out.println("3. Adreces");
        System.out.println("4. Targeta");
        System.out.println("5. Salir");
        int op = scanner.nextInt();
        switch (op) {
            case 1:
                settings(username, client);
                break;
            case 2:
                comprasRealizadas(username, client);
                break;
            case 3:
                System.out.println("*******************************");
                System.out.println("**      Dades Personals       **");
                System.out.println("Usuari: " + client.getNom() + " " + client.getCognom1() + " " + username);
                System.out.println("********************************");
                AdrecaDAO adrecaDAO = new AdrecaDAOImpl();
                System.out.println("Adreca Disponibles:");
                List<Adreca> mostrarAdreça = adrecaDAO.mostrarAdreça();
                for (int i = 0; i < mostrarAdreça.size(); i++) {
                    Adreca adreca = mostrarAdreça.get(i);
                    System.out.println("[" + i + "]" + " " + adreca.getCarrer() + " " + adreca.getNumero() + " " + adreca.getPorta());
                }
                System.out.println("Introduce el ID del cliente:");
                int client_id = scanner.nextInt();
                System.out.println("Elige una opcion");
                System.out.println("1) Añadir adreca de enviamiento");
                System.out.println("2) Salir");
                int op2 = scanner.nextInt();
                switch (op2) {
                    case 1:
                        afegirAdreca(client_id);
                        dadesPersonals(username);
                    case 2:
                        dadesPersonals(username);
                        return;
                }
                break;
            case 4:
                System.out.println("*******************************");
                System.out.println("**      Dades Personals       **");
                System.out.println("Usuari: " + client.getNom() + " " + client.getCognom1() + " " + username);
                System.out.println("********************************");
                System.out.println("Targetas Disponibles");
                TargetaDAO targetaDAO = new TargetaDAOImpl();
                List<Targeta> mostrarTargetas = targetaDAO.mostrarTargetas();
                for (int i = 0; i < mostrarTargetas.size(); i++) {
                    Targeta targeta = mostrarTargetas.get(i);
                    System.out.println("[" + i + "] " + targeta.getNumero() + " " + targeta.getTipus() + " " + targeta.getData_caducitat());
                }
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Elige una opcion");
                System.out.println("1. Afegir Targeta");
                System.out.println("2. Eliminar targeta Disponible");
                System.out.println("3. Salir");
                int op3 = scanner1.nextInt();
                switch (op3) {
                    case 1:
                        afegirTargetaEnviamiento();
                        dadesPersonals(username);
                        break;
                    case 2:
                        eliminarTarjeta(mostrarTargetas);
                        dadesPersonals(username);
                        return;
                    case 3:
                        dadesPersonals(username);
                        return;
                }
                break;
            case 5:
                return;
            default:
                System.out.println("Opción inválida. Seleccione una opción válida.");
                break;
        }

    }

    private static void settings(String username, Client client) {
        System.out.println("*******************************");
        System.out.println("**      Dades Personals       **");
        System.out.println("Usuari: " + client.getNom() + " " + client.getCognom1() + " " + username);
        System.out.println("********************************");
        System.out.println("1. Modifica dades personals");
        System.out.println("2. Canvi password");
        System.out.println("3. Salir");
        int op = scanner.nextInt();
        switch (op) {
            case 1:
                ModificardadesPersonals(username, client);
                break;
            case 2:
                cambiarcontraseña(username);
                dadesPersonals(username);
                break;
            case 3:
                dadesPersonals(username);
                return;
            default:
                System.out.println("Opción inválida. Seleccione una opción válida.");
                break;
        }

    }

    private static void cambiarcontraseña(String username) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce la contraseña actual:");
        String contraseñaActual = scanner.nextLine();

        System.out.println("Introduce la nueva contraseña:");
        String nuevaContraseña1 = scanner.nextLine();

        System.out.println("Confirma la nueva contraseña:");
        String nuevaContraseña2 = scanner.nextLine();
        if (nuevaContraseña1.equals(nuevaContraseña2)) {
            ClientDAO clientDAO = new ClientDAOImpl();
            boolean contraseñaCambiada = clientDAO.cambiarContraseña(username, contraseñaActual, nuevaContraseña1);

            if (contraseñaCambiada) {
                System.out.println("Contraseña cambiada correctamente.");
            } else {
                System.out.println("No se pudo cambiar la contraseña. Verifica que la contraseña actual sea correcta.");
            }
        } else {
            System.out.println("Las contraseñas no coinciden. Vuelve a intentarlo.");
        }
    }

    private static void ModificardadesPersonals(String username, Client client) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Que nuevo nombre quieres: " + "(" + client.getNom() + ")");
        String nuevoNom = scanner.nextLine();
        System.out.println("Que nuevo apellido1 quieres: " + "(" + client.getCognom1() + ")");
        String nuevoCognom1 = scanner.nextLine();
        System.out.println("Que nuevo apellido quieres:" + "(" + client.getCognom2() + ")");
        String nuevoCognom2 = scanner.nextLine();

        ClientDAO clientDAO = new ClientDAOImpl();
        clientDAO.modificarAtributosCliente(username, nuevoNom, nuevoCognom1, nuevoCognom2);

        dadesPersonals(username);
    }

    private static void comprasRealizadas(String username, Client client) {
        System.out.println("*******************************");
        System.out.println("**          Compres          **");
        System.out.println("Usuari: " + client.getNom() + " " + client.getCognom1() + " " + username);
        System.out.println("********************************");

        Detall_CompraDAO detall_compraDAO = new Detall_CompraDAOImpl();
        List<Detall_Compra> detallesCompra = detall_compraDAO.obtenerDetallesCompra();
        int contador = 0;
        double totalCistella = 0.0;
        for (Detall_Compra detalle : detallesCompra) {
            int producteId = detalle.getProducte_id();
            ProducteDAO producteDAO = new ProducteDAOImpl();
            Producte producte = producteDAO.obtenerProductoPorId(producteId);

            System.out.println(contador + " " + producte.getNom() + " " + producte.getPvp() + " " + detalle.getUnitats_producte() + " unitats");
            double subtotal = producte.getPvp() * detalle.getUnitats_producte();
            totalCistella += subtotal;
            contador++;
        }
        System.out.println(totalCistella);
        dadesPersonals(username);
    }

    private static void afegirTargetaEnviamiento() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el tipo de tarjeta:");
        String tipus = scanner.nextLine();

        System.out.println("Ingrese el número de tarjeta:");
        int numero = scanner.nextInt();

        System.out.println("Ingrese la fecha de caducidad de la tarjeta (en formato dd/MM/yyyy):");
        String fechaCaducidad = scanner.next();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data_caducitat;
        try {
            data_caducitat = formatter.parse(fechaCaducidad);
        } catch (ParseException e) {
            System.out.println("Fecha de caducidad inválida. Inténtelo de nuevo.");
            return;
        }

        System.out.println("Ingrese el código de seguridad de la tarjeta:");
        int codi_seguretat = scanner.nextInt();

        int client_id = verificarCliente();

        TargetaDAO targetaDAO = new TargetaDAOImpl();
        targetaDAO.registre(tipus, numero, data_caducitat, codi_seguretat, client_id);
    }

    private static void eliminarTarjeta(List<Targeta> mostrarTargetas) {
        System.out.println("Seleccione el número de tarjeta a eliminar: " + " (El numero que esta al lado de la lista de tarjeta)");

        int opcion = scanner.nextInt();

        if (opcion >= 0 && opcion <= mostrarTargetas.size()) {
            Targeta tarjetaEliminar = mostrarTargetas.get(opcion);
            TargetaDAO targetaDAO = new TargetaDAOImpl();
            boolean eliminacionExitosa = targetaDAO.eliminarTarjeta(tarjetaEliminar.getId());

            if (eliminacionExitosa) {
                System.out.println("La tarjeta ha sido eliminada correctamente.");
            } else {
                System.out.println("No se ha podido eliminar la tarjeta.");
            }
        } else {
            System.out.println("Número de tarjeta inválido.");
        }
    }
}
