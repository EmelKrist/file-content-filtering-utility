# Утилита фильтрации содержимого файлов.
Версия Java: 17 (Oracle OpenJDK version 17.0.7)   
Системы сборки: не используется   
Сторонние библиотеки: не используется   
____
### Описание.
Консольное приложение для записи содержимого файлов в разные файлы по типу данных. 
Принимает на вход один и более файлов, построчно читает каждый и фильтрует полученные целые числа, 
вещественные числа и строки по выходным файлам, создающимся под соответствующий тип данных, если таковой был обнаружен.
Утилита предоставляет в качестве дополнительного функционала возможность задать путь и префикс имен для файлов с результатами,
а также менять режим добавления данных в результирующие файлы (добавлять к имеющимся/перезаписывать). Плюс ко всему приложение 
ведет статистику двух видов (краткую/полную) по каждому типу данных и способно предоставлять ее по запросу пользователя.
Утилита обеспечена функционалом для обработки ошибок и предоставления пользователю информации по их устранению.
____
### Запуск.
Для начала нужно зайти в папку проекта через командную строку, используя команду:
```
cd <путь_к_проекту>
```
После чего можно запустить утилиту через исполняемый файл, прописав следующую команду:
```
java -jar file-content-filtering-utility.jar [-o <выходной_путь>] [-p <выходной_префикс>] [-a] [-s] [-f] <входные_файлы>
```
**Аргумент** | **Назначение**
:----|:------:
-o | аргумент для указания пути результатов <выходной_путь>
-p | аргумент для указания префикса <выходной_префикс> имен выходных файлов
-a | аргумент для активации режима добавления данных в существ. файлы (по-умолчанию файлы перезаписываются)
-s | аргумент для активации вывода краткой статистики
-f | аргумент для активации вывода полной статистики
<входные_файлы> | файлы, содержимое которых фильтруется (с расширением)
____
### Результат.
Результат работы приложения можно посмотреть в директориях input/ и output/, где располагаются исходные файлы и файлы с результатами фильтрации соответственно.