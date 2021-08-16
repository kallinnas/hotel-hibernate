package thread;

class Incremenator extends Thread {
    private volatile boolean mIsIncrement = true;

    public void changeAction() {
        mIsIncrement = !mIsIncrement;
    }

    @Override
    public void run() {
        do {
            if (!Thread.interrupted()) {
                if (mIsIncrement) Program.mValue++;
                else Program.mValue--;

                //Вывод текущего значения переменной
                System.out.print(Program.mValue + " ");
            } else
                return;        //Завершение потока
            try {
                Thread.sleep(1000);
                //Метод sleep() может выполняться либо заданное кол-во времени
                // либо до тех пор пока он не будет остановлен прерыванием
                // (в этом случае он сгенерирует исключение InterruptedException).
            } catch (InterruptedException e) {
                return;    //Завершение потока после прерывания
            }
        }
        while (true);
    }
}

class Program {
    //Переменая, которой оперирует инкременатор
    static int mValue = 0;

    static Incremenator mInc;    //Объект побочного потока

    public static void main(String[] args) {
        mInc = new Incremenator();    //Создание потока

        System.out.print("Значение = ");

        mInc.start();    //Запуск потока

        //Троекратное изменение действия инкременатора
        //с интервалом в i*2 секунд
        for (int i = 1; i <= 3; i++) {
            try {
                Thread.sleep(i * 2 * 1000); //Ожидание в течении i*2 сек.
            } catch (InterruptedException e) {//ignore
            }

            mInc.changeAction();    //Переключение действия
        }

        mInc.interrupt();    //Инициация завершения побочного потока
    }
}
