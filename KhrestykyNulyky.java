import java.util.Scanner;
/**
 * Хрестики-Нулики: Консольна гра для двох, не-OO версія
 * адаптована і українізована версія, оригінал коду тут: http://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe.html 
 */

public class KhrestykyNulyky {
	
   // Фіксовані змінні, які вказують на вміст клітинки/комірки 
   public static final int POROZHNYA = 0;
   public static final int KHRESTYK = 1;
   public static final int NULYK = 2;
 
   // Фіксовані змінні, які вказують на "стан справ" у грі (статус гри)
   public static final int GRA_TRYVAYE = 0; // гра триває
   public static final int GRA_ZAKINCHYLAS_NICHYYA = 1; // гра закінчилась нічиєю
   public static final int GRA_ZAKINCHYLAS_PEREMOHOYU_KHRESTYKA = 2; // гра закінчилась перемогою хрестиків
   public static final int GRA_ZAKINCHYLAS_PEREMOHOYU_NULYKA = 3; // гра закінчилась перемогою нуликів
 
   // Змінні для зберігання інформації про ігрове поле і "стан справ"
   public static final int RYADKIV = 3, STOVPCHYKIV = 3; // кількість рядків і стовпчиків
   public static int[][] sitka = new int[RYADKIV][STOVPCHYKIV]; // ігрове поле у вигляді 2-вимірного масиву
                                                      // кожна клітинка містить одне з значень: (POROZHNYA, KHRESTYK, NULYK)
   public static int statusGry;  	// "стан справ" (статус гри) в даний момент
                                    // (GRA_TRYVAYE, GRA_ZAKINCHYLAS_NICHYYA, GRA_ZAKINCHYLAS_PEREMOHOYU_KHRESTYKA, GRA_ZAKINCHYLAS_PEREMOHOYU_NULYKA)
   public static int aktyvnyiGravets; // активний гравець, чий хід зараз буде відбувається (KHRESTYK or NULYK)
   public static int aktyvnyiRyadok, aktyvnyiStovpchyk; // активний рядок і активний стовпчик
 
   public static Scanner in = new Scanner(System.in); // отримання введення гравців за допомогою Scanner
 
   /** метод main (виконання програми починається тут) */
   public static void main(String[] args) {
      // ініціалізуємо гру (малюємо гральне поле)
      pochatyGru();
      
      // наступний крок гравців (по черзі)    
      do {
    	  
         otrymatyVvedennyaGravtsya(aktyvnyiGravets); // отримати введення від користувача і записати в масив
         proanalizyvatySitku(aktyvnyiGravets, aktyvnyiRyadok, aktyvnyiStovpchyk); // перевірити "статус гри"
         vyvestySitku(); // вивести "гральне поле"
         
         // перевірити "статус гри" і якщо гра закінчилась - вивести результати
         if (statusGry == GRA_ZAKINCHYLAS_PEREMOHOYU_KHRESTYKA) {
            System.out.println("'X' переміг! Вітаємо!");
         } else if (statusGry == GRA_ZAKINCHYLAS_PEREMOHOYU_NULYKA) {
            System.out.println("'O' переміг! Вітаємо!");
         } else if (statusGry == GRA_ZAKINCHYLAS_NICHYYA) {
            System.out.println("Гра закінчилась внічию! До побачення!");
         }
         
         // Змінити активного гравця з Х на О або з О на Х
         aktyvnyiGravets = (aktyvnyiGravets == KHRESTYK) ? NULYK : KHRESTYK;
         
      } while (statusGry == GRA_TRYVAYE); // повторити ще раз, якщо гра не закінчилась
   }
 
   /** Ініціалізувати (запустити) гру: очистити "гральне поле", 
    * скинути статус на "гра триває" і запам'ятати, що хрестики починають  */
   public static void pochatyGru() {
      for (int ryad = 0; ryad < RYADKIV; ++ryad) {
         for (int stovp = 0; stovp < STOVPCHYKIV; ++stovp) {
            sitka[ryad][stovp] = POROZHNYA;  // всі комірки - порожні
         }
      }
      statusGry = GRA_TRYVAYE; // можна грати, поки що ніхто не переміг і гра не закінчилась нічиєю
      aktyvnyiGravets = KHRESTYK;  // хрестик починає гру
      vyvestySitku(); // вивести порожнє "гральне поле"
   }
 
   /** Гравець "vvedennya" (Х або О) ставить свій значок, метод перевіряє чи гравець ставить в дозволеному місці.
       і оновлює глобальні змінні "aktyvnyiRyadok" та "aktyvnyiStovpchyk". */
   public static void otrymatyVvedennyaGravtsya(int vvedennya) {
      boolean vvedennyaDijsne = false;  // для перевірки, чи введення користувача дозволене (чи не виходить за межі дозволеного або вказує на вже використану комірку)
      
      do {
         if (vvedennya == KHRESTYK) {
            System.out.print("Гравець 'X', введіть рядок (від 1 до 3) і стовпчик (від 1 до 3) через пробіл: ");
         } else {
            System.out.print("Гравець 'O', введіть рядок (від 1 до 3) і стовпчик (від 1 до 3) через пробіл: ");
         }
         int ryad = in.nextInt() - 1;  // оскільки порядковий номер елементів масиву починаєтсья з 0, а ми просимо користувача ввести числа починаючи з 1, нам потрібно відняти 1 від введення
         int stovp = in.nextInt() - 1;
         if (ryad >= 0 && ryad < RYADKIV && stovp >= 0 && stovp < STOVPCHYKIV && sitka[ryad][stovp] == POROZHNYA) {
            aktyvnyiRyadok = ryad;
            aktyvnyiStovpchyk = stovp;
            sitka[aktyvnyiRyadok][aktyvnyiStovpchyk] = vvedennya;  // записати введення користувача в "ігрове поле"
            vvedennyaDijsne = true;  // введення (дійсне) дозволене
         } else {
            System.out.println("Вибране розміщення (" + (ryad + 1) + "," + (stovp + 1)
                  + ") не може бути використане. Спробуйте ще раз...");
         }
      } while (!vvedennyaDijsne);  // повторювати доти, доки введення не буде дійсним
      
   }
 
   /** Перевірити/Оновити стан справ у грі (чи хтось переміг, чи гра закінчилась нічиєю
    *  або чи можна продовжувати грати), тобто змінну "statusGry" після того як гравець ввів свій варіант
       (aktyvnyiRyadok, aktyvnyiStovpchyk). */
   public static void proanalizyvatySitku(int vvedennya, int aktyvnyiRyadok, int aktyvnyiStovpchyk) {
      if (chyPeremig(vvedennya, aktyvnyiRyadok, aktyvnyiStovpchyk)) {  // перевірити чи введення принесло перемогу активному гравцеві
         statusGry = (vvedennya == KHRESTYK) ? GRA_ZAKINCHYLAS_PEREMOHOYU_KHRESTYKA : GRA_ZAKINCHYLAS_PEREMOHOYU_NULYKA;
      } else if (chyNichyya()) {  // перевірити чи нічия
         statusGry = GRA_ZAKINCHYLAS_NICHYYA;
      }
      // в іншому випадку statusGry (стату гри) не змінився, тобто (GRA_TRYVAYE).
   }
 
   /** Поверне true якщо нічия (більше не лишилось порожніх комірок) */
   public static boolean chyNichyya() {
      for (int ryad = 0; ryad < RYADKIV; ++ryad) {
         for (int stovp = 0; stovp < STOVPCHYKIV; ++stovp) {
            if (sitka[ryad][stovp] == POROZHNYA) {
               return false;  // знайдено порожню комірку, значить гра може продовжуватись
            }
         }
      }
      return true;  // порожніх комірок не знайдено, значить гра закінчилась нічиєю
   }
 
   
   /** Поверне true якщо гравець "vvedennya" переміг після запису "vvedennya" у вибрану комірку
       (aktyvnyiRyadok + aktyvnyiStovpchyk) */
   public static boolean chyPeremig(int vvedennya, int aktyvnyiRyadok, int aktyvnyiStovpchyk) {
      return (sitka[aktyvnyiRyadok][0] == vvedennya         // 3-в-рядок
                   && sitka[aktyvnyiRyadok][1] == vvedennya
                   && sitka[aktyvnyiRyadok][2] == vvedennya
              || sitka[0][aktyvnyiStovpchyk] == vvedennya      // 3-в-стовпчик
                   && sitka[1][aktyvnyiStovpchyk] == vvedennya
                   && sitka[2][aktyvnyiStovpchyk] == vvedennya
              || aktyvnyiRyadok == aktyvnyiStovpchyk            // 3-по-діагоналі-зліва-направо
                   && sitka[0][0] == vvedennya
                   && sitka[1][1] == vvedennya
                   && sitka[2][2] == vvedennya
              || aktyvnyiRyadok + aktyvnyiStovpchyk == 2  // 3-по-діагоналі-справа-наліво
                   && sitka[0][2] == vvedennya
                   && sitka[1][1] == vvedennya
                   && sitka[2][0] == vvedennya);
   }
 
   
   /** вивести ігрове поле (змінну sitka) в консоль */
   public static void vyvestySitku() {
      for (int ryad = 0; ryad < RYADKIV; ++ryad) {
         for (int stovp = 0; stovp < STOVPCHYKIV; ++stovp) {
            vyvestyKomirku(sitka[ryad][stovp]); // вивести кожну з комірок
            if (stovp != STOVPCHYKIV - 1) {
               System.out.print("|");   // вивести вертикальний розділювач сітки/поля
            }
         }
         System.out.println();
         if (ryad != RYADKIV - 1) {
            System.out.println("-----------"); // вивести горизонтальний розділювач сітки/поля
         }
      }
      System.out.println();
   }
 
   /** Вивести вказаний варіант в комірку */
   public static void vyvestyKomirku(int variant) {
      switch (variant) {
         case POROZHNYA:  System.out.print("   "); break;
         case NULYK: System.out.print(" O "); break;
         case KHRESTYK:  System.out.print(" X "); break;
      }
   }
}
