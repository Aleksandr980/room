Напишите мини-приложение для подсчёта слов. Интерфейс должен позволять добавлять в словарь новое слово, просматривать список первых слов в словаре, искать количество совпадений, а также очищать данные приложения.
Создайте базу данных, используя Room.
В качестве модели необходимо объявить сущность, содержащую слово (оно же используется в качестве ключа) и количество его повторений.
На главный экран добавьте TextInput и кнопку «Добавить». Нажимая на неё, мы должны либо добавлять новое слово в словарь (если оно не встречается), либо увеличивать счётчик повторений на единицу.
Подпишитесь на обновление таблицы, используя flow. Добавьте на экран текстовое поле, куда будут выводиться первые пять встречающихся слов.
Добавьте кнопку «Очистить», нажатие на которую удаляло бы все данные из БД.
Добавьте проверку на ввод слова. Необходимо блокировать добавление пустых строк и слов, содержащих пробелы, цифры, точки и запятые (допустимы только буквы и дефисы). При попытке пользователя добавить такое сочетание выводите соответствующее сообщение на экран.
