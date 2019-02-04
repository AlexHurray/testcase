Демонстрационный проект

Для подключения библиотеки надо добавить строки в build.gradle(Module: app)
	
	implementation project(':admodule')
    implementation project(':admodule_annotations')
    annotationProcessor project(':admodule_compiler')
	
После этого активность, после показа которой минуту надо открывать рекламу, помечается аннотацией @AddAdvertisement

Enjoy!