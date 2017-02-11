import java.util.Scanner;

/**
 * Програма-гра Хрестики-Нулики (в консолі)
 * @author UkrainianVirtualAcademy (Українська Віртуальна Академія)
 *https://www.youtube.com/channel/UCvcmcsG1rUWbXoapjd81EYQ 
 */
public class TicTacToe3 {
	// фіксовані змінні, які вказують на вміст комірки
	public static final String POROZHNYA = "   ", KHRESTYK = " X ", NULYK = " O ";
	// активний гравець, чий хід зараз буде відбувається (KHRESTYK or NULYK)
	public static String aktyvnyiGravets;
	// змінні для зберігання інфо про ігрове поле і "стан гри"
	// Змінні для зберігання інформації про ігрове поле і "стан справ"
	// кількість рядків і стовпчиків
	public static final int RYADKIV = 3, STOVPCHYKIV = 3;
	// ігрове поле у вигляді 2-вимірного масиву
	// кожна клітинка містить одне з значень: (POROZHNYA, KHRESTYK, NULYK)
	public static String[][] sitka = new String[RYADKIV][STOVPCHYKIV];
	// "стан справ" (статус гри) в даний момент
	public static int statusGry;
	// Фіксовані змінні, які вказують на "стан справ" у грі (статус гри)
	public static final int STATUS_GRA_TRYVAYE = 0, STATUS_NICHYYA = 1, STATUS_PEREMOGA_X = 3, STATUS_PEREMOGA_O = 4;
	// отримання введення гравців за допомогою Scanner
	public static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		// ініціалізуємо гру (малюємо гральне поле)
		PochatyGru();
		// наступний крок гравців (по черзі)
		do {
			// отримати введення від користувача і записати в масив
			OtrymatyVvedennyaGravtsya();
			// перевірити "статус гри"
			ProanalizuvatySitku();
			VyvestySitku();
			// перевірити "статус гри" і якщо гра закінчилась - вивести результати
			if (statusGry==STATUS_PEREMOGA_X){
				System.out.println("'X' переміг! Вітаємо!");
			} else if (statusGry==STATUS_PEREMOGA_O){
				System.out.println("'O' переміг! Вітаємо!");
			} else if (statusGry==STATUS_NICHYYA){
				System.out.println("Гра закінчилась внічию! До побачення!");
			} 
			// Змінити активного гравця з Х на О або з О на Х
			aktyvnyiGravets = (aktyvnyiGravets==KHRESTYK?NULYK:KHRESTYK);
		}
		while (statusGry==STATUS_GRA_TRYVAYE);
		
	}

	/**
	 * Ініціалізувати (запустити) гру: очистити "гральне поле", скинути статус
	 * на "гра триває" і запам'ятати, що хрестики починають
	 */
	public static void PochatyGru() {
		for (int ryad = 0; ryad < RYADKIV; ryad++) {
			for (int stovp = 0; stovp < STOVPCHYKIV; stovp++) {
				sitka[ryad][stovp] = POROZHNYA; // всі комірки - порожні
			}
		}
		aktyvnyiGravets = KHRESTYK; // хрестик починає гру
		VyvestySitku(); // вивести порожнє "гральне поле"
	}

	/**
	 * Гравець "vvedennya" (Х або О) ставить свій значок, метод перевіряє чи
	 * гравець ставить в дозволеному місці. 
	 */
	public static void OtrymatyVvedennyaGravtsya() {
		// для перевірки, чи введення користувача дозволене (чи не виходить за
		// межі дозволеного абo вказує на вже використану комірку)
		boolean vvedennyaDijsne = false;
		do {
			System.out
					.println("Гравець '" + aktyvnyiGravets + "', введіть рядок (1-3) і стовпчик (1-3) через пробіл: ");
			// оскільки порядковий номер елементів масиву (sitka[][]) починаєтсья з 0, а ми
			// просимо користувача ввести числа починаючи з 1, нам потрібно
			// відняти 1 від введення
			int ryad = in.nextInt() - 1;
			int stovp = in.nextInt() - 1;
			if (ryad >= 0 && ryad < RYADKIV && stovp >= 0 && stovp < STOVPCHYKIV && sitka[ryad][stovp] == POROZHNYA) {
				// записати введення користувача в "ігрове поле"
				sitka[ryad][stovp] = aktyvnyiGravets;
				// введення (дійсне) дозволене
				vvedennyaDijsne = true;
			} else {
				System.out.println("Вибране розміщення (" + (ryad + 1) + "," + (stovp + 1)
						+ ") не може бути використане. Спробуйте ще раз...");
			}
		} while (!vvedennyaDijsne); // повторювати доти, доки введення не буде
		// дійсним
	}

	public static void ProanalizuvatySitku() {
		
		String peremozhets = ZnajtyPeremozhtsya();
		if (peremozhets.equals(KHRESTYK)){
			statusGry = STATUS_PEREMOGA_X;
		} else if (peremozhets.equals(NULYK)){
			statusGry = STATUS_PEREMOGA_O;
		} else if (ChyVsiKlitynkyZapovneni()){
			statusGry = STATUS_NICHYYA;
		} else {
			statusGry = STATUS_GRA_TRYVAYE;
		}
	}
	
	/** Поверне true якщо нічия (більше не лишилось порожніх комірок) */
	public static boolean ChyVsiKlitynkyZapovneni() {
		for (int ryad=0; ryad<RYADKIV; ryad++){
			for (int stovp=0; stovp < STOVPCHYKIV; stovp++){
				if (sitka[ryad][stovp]==POROZHNYA){
					return false; // знайдено порожню комірку
				}
			}
		}
		return true; // порожніх комірок не знайдено
	}

	public static String ZnajtyPeremozhtsya() {

		int iKilkistOdnakovykh;
		// 3-в-рядок
		for (int ryad = 0; ryad < RYADKIV; ryad++) {
			iKilkistOdnakovykh = 0;
			for (int stovp = 0; stovp < STOVPCHYKIV; stovp++) {
				if (sitka[ryad][0] != POROZHNYA && sitka[ryad][0] == sitka[ryad][stovp]) {
					iKilkistOdnakovykh++;
				}
			}
			if (iKilkistOdnakovykh == 3) {
				return sitka[ryad][0];
			}
		}

		// 3-в-стовпчик
		for (int stovp = 0; stovp < STOVPCHYKIV; stovp++) {
			iKilkistOdnakovykh = 0;
			for (int ryad = 0; ryad < RYADKIV; ryad++) {
				if (sitka[0][stovp] != POROZHNYA && sitka[0][stovp] == sitka[ryad][stovp]) {
					iKilkistOdnakovykh++;
				}
			}
			if (iKilkistOdnakovykh == 3) {
				return sitka[0][stovp];
			}
		}

		if (sitka[0][0] != POROZHNYA && sitka[0][0] == sitka[1][1] && sitka[0][0] == sitka[2][2]) {
			return sitka[0][0];
		}

		// 3-по-діагоналі-справа-наліво
		if (sitka[0][2] != POROZHNYA && sitka[1][1] == sitka[0][2] && sitka[2][0] == sitka[0][2]) {
			return sitka[0][2];
		}
		
		return POROZHNYA;
	}
	
	/** вивести ігрове поле (змінну sitka) в консоль */
	public static void VyvestySitku() {
		for (int ryad = 0; ryad < RYADKIV; ryad++) {
			for (int stovp = 0; stovp < STOVPCHYKIV; stovp++) {
				System.out.print(sitka[ryad][stovp]); // вивести кожну з комірок
				if (stovp != STOVPCHYKIV - 1) {
					System.out.print("|"); // вивести вертикальний розділювач сітки/поля
				}
			}
			System.out.println();
			if (ryad != RYADKIV - 1) {
				System.out.println("-----------"); // вивести горизонтальний розділювач сітки/поля
			}
		}
		System.out.println();
	}

}