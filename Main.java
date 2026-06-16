import java.util.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        PortalDeNoticias portal = new PortalDeNoticias();
        Integer opcion = -1;
        
        System.out.println("======================================");
        System.out.println("   BIENVENIDO AL PORTAL DE NOTICIAS  ");
        System.out.println("======================================");
        portal.cargarSistema();

        do {
            System.out.println("\n---- MENU DE OPCIONES ----\n");
            System.out.println("1. Registrar autor");
            System.out.println("2. Registrar lector");
            System.out.println("3. Publicar noticia");
            System.out.println("4. Comentar noticia");
            System.out.println("5. Listar noticias por anio");
            System.out.println("6. Listar noticias del ultimo mes");
            System.out.println("7. Listar noticias de un autor");
            System.out.println("0. Guardar datos y cerrar el sistema");

            try {
                System.out.print("\n Ingrese la opcion que desea realizar: ");
                opcion = Integer.parseInt(teclado.nextLine());

                switch (opcion) {
                    case 1:{
                        System.out.println("\n--- REGISTRO DE AUTOR ---\n");
                        System.out.print("Ingrese DNI: ");
                        Integer dni = Integer.parseInt(teclado.nextLine());
                        System.out.print("Ingrese Nombre completo: ");
                        String nombre = teclado.nextLine();
                        System.out.print("Ingrese Medio periodistico: ");
                        String medio = teclado.nextLine();

                        portal.registrarAutor(dni, nombre, medio);
                        System.out.println("¡Autor registrado con exito!");
                        break;
                    }

                    case 2:{
                        System.out.println("\n--- REGISTRO DE LECTOR ---\n");
                        System.out.print("Ingrese DNI: ");
                        Integer dni = Integer.parseInt(teclado.nextLine());
                        System.out.print("Ingrese Nombre completo: ");
                        String nombre = teclado.nextLine();
                        System.out.print("Ingrese Edad: ");
                        Integer edad = Integer.parseInt(teclado.nextLine());

                        portal.registrarLector(dni, nombre, edad);
                        System.out.println("¡Lector registrado con exito!");
                        break;
                    }
                    
                    case 3: {
                        System.out.println("\n--- PUBLICAR NUEVA NOTICIA ---\n");
                        System.out.print("Ingrese el Titulo de la noticia: ");
                        String titulo = teclado.nextLine();
                        System.out.print("Ingrese el Cuerpo/Detalle: ");
                        String detalle = teclado.nextLine();
                        
                        System.out.println("Fecha de publicacion:");
                        System.out.print("  Dia (DD): ");
                        Integer dia = Integer.parseInt(teclado.nextLine());
                        System.out.print("  Mes (MM): ");
                        Integer mes = Integer.parseInt(teclado.nextLine());
                        System.out.print("  Anio (YYYY): ");
                        Integer anio = Integer.parseInt(teclado.nextLine());
                        
                        System.out.print("Ingrese el DNI del Autor responsable: ");
                        Integer dniAutor = Integer.parseInt(teclado.nextLine());

                        portal.publicarNoticia(titulo, detalle, dia, mes, anio, dniAutor);
                        System.out.println("¡Noticia publicada con exito en el portal!");
                        break;
                    }

                    case 4:{
                        System.out.println("\n--- DEJAR UN COMENTARIO ---\n");
                        System.out.print("Ingrese el Titulo exacto de la noticia que desea comentar: ");
                        String tituloNoticia = teclado.nextLine();
                        System.out.print("Ingrese su DNI de Lector: ");
                        Integer dniLector = Integer.parseInt(teclado.nextLine());
                        System.out.print("Escriba su comentario: ");
                        String texto = teclado.nextLine();

                        portal.comentarNoticia(texto, tituloNoticia, dniLector);
                        System.out.println("¡Comentario publicado exitosamente!");
                        break;
                    }

                    case 5:{
                        System.out.println("\n--- LISTAR NOTICIAS POR ANIO ---\n");
                        System.out.println("1. Ver noticias de ESTE ANIO");
                        System.out.println("2. Buscar un ANIO en especifico");
                        System.out.print("Elija una opcion: ");

                        Integer subOpcion = Integer.parseInt(teclado.nextLine());

                        if (subOpcion == 1) {
                            Integer anioActual = LocalDate.now().getYear();
                            System.out.println("\n--- NOTICIAS DEL ANIO " + anioActual + " ---");
                            portal.listarNoticiasPorAnio(anioActual);
                        } else if (subOpcion == 2) {
                            System.out.print("Ingrese el Anio que desea consultar (Ej: 2024): ");
                            Integer anioBuscado = Integer.parseInt(teclado.nextLine());
                            System.out.println("\n--- NOTICIAS DEL ANIO " + anioBuscado + " ---");
                            portal.listarNoticiasPorAnio(anioBuscado);
                        } else {
                            System.out.println("Opcion incorrecta. Volviendo al menu...");
                        }
                        break;
                    }

                    case 6:{
                        System.out.println("\n--- NOTICIAS DEL ULTIMO MES ---\n");
                        portal.listarNoticiasPorMes();
                        break;
                    }

                    case 7:{
                        System.out.println("\n--- NOTICIAS DE UN AUTOR ---\n");
                        System.out.print("Ingrese el DNI del Autor: ");
                        Integer dniAutor = Integer.parseInt(teclado.nextLine());
                        portal.listarNoticiasPorAutor(dniAutor);
                        break;
                    }

                    case 0:{
                        System.out.println("\n-- Guardando datos en el sistema ...\n");
                        portal.guardarSistema();
                        System.out.println("¡Gracias por usar el Portal de Noticias!");
                        break;
                    }

                    default:{
                        System.out.println("La opcion ingresada no es valida. Por favor, intente de nuevo.");
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                // Atrapa si el usuario escribe letras en lugar de números en el menú o fechas
                System.err.println("Error de ingreso: Por favor, ingrese unicamente números donde corresponda.");
                opcion = -1; // Reseteamos para que no se salga del menú
                
            } catch (Exception e) {
                // Atrapa errores de lógica (ej: DniDuplicadoException, fechas futuras, vacíos)
                System.err.println(e.getMessage());
            }
            
        } while (opcion != 0);

        teclado.close();
    }
}