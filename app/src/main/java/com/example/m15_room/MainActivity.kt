/*package com.example.m15_room

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.m15_room.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var bindind: ActivityMainBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindind.root)


        val viewModel: MainViewModel by viewModels {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    //val dictionaryDao: DictionaryDao = App.db.dictionaryDao()
                    val dictionaryDao: DictionaryDao = (application as App).db.dictionaryDao()
                    return MainViewModel(dictionaryDao) as T
                }
            }
        }
        // val dictionaryDao: DictionaryDao = App.db.dictionaryDao()
        val dictionaryDao: DictionaryDao = (application as App).db.dictionaryDao()

        // На главный экран добавьте TextInput и кнопку «Добавить».
        // Нажимая на неё, мы должны либо добавлять новое слово в словарь
        // (если оно не встречается), либо увеличивать счётчик повторений на единицу.
        //проверяйте в потоке getAll() наличие этого слова, если есть,
        // то вызывайте update у dao и передавайте туда слово с увеличенным значением count,
        //если нет, то можно вызывать insert с count = 1
        dictionaryDao.getAll().asLiveData().observe(this) { list ->
            bindind.textView.text = ""
            list.forEach {
                val text = "\b${it.word}\b"
                bindind.textView.append(text)
            }
        }
        //вариант 1
        bindind.add.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val inputText = bindind.textInput.text.toString()
                val dictionary = Dictionary(inputText, 1)
                val numberOfRepetitions = dictionary.numberOfRepetitions
                val numberOfRepetitionsNew = numberOfRepetitions + 1
                val dictionaryNew = Dictionary(inputText, numberOfRepetitionsNew)

                withContext(Dispatchers.IO) {
                        if (viewModel.dictionaryDao.toString().contains(inputText) &&
                                dictionary.numberOfRepetitions >1) {
                        viewModel.onUpdate(dictionaryNew)
                    } else viewModel.onAdd(dictionary)
                }
            }
        }

        //вариант 2

//        bindind.add.setOnClickListener {
//val texts =  viewModel.allDictionarys.onEach {
//    bindind.textView.text = it.toString()
//}
//            lifecycleScope.launch(Dispatchers.IO) {
//                val dictionary = Dictionary(null, bindind.textInput.text.toString(), 0)
//                if (texts == bindind.textInput.text) {
//                    viewModel.onUpdate(dictionary)
//                } else viewModel.onAdd(dictionary)
//            }


        bindind.clear.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                //  val dictionary = Dictionary(null, bindind.textInput.text.toString(), 0)
                viewModel.onDelete()
                // viewModel.onUpdate(dictionary)
            }
        }
        //Подпишитесь на обновление таблицы, используя flow. Добавьте на экран текстовое поле,
        // куда будут выводиться первые пять встречающихся слов.

        viewModel.allDictionarys.onEach {
            bindind.textView.text = it.toString()
        }.launchIn(lifecycleScope)

    }
}

 */

package com.example.m15_room

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.m15_room.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var bindind: ActivityMainBinding

    @SuppressLint("SuspiciousIndentation", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindind.root)

        val viewModel: MainViewModel by viewModels {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val dictionaryDao: DictionaryDao = (application as App).db.dictionaryDao()
                    return MainViewModel(dictionaryDao) as T
                }
            }
        }
        // На главный экран добавьте TextInput и кнопку «Добавить».
        // Нажимая на неё, мы должны либо добавлять новое слово в словарь
        // (если оно не встречается), либо увеличивать счётчик повторений на единицу.
        //проверяйте в потоке getAll() наличие этого слова, если есть,
        // то вызывайте update у dao и передавайте туда слово с увеличенным значением count,
        //если нет, то можно вызывать insert с count = 1
        viewModel.allDictionarys.asLiveData().observe(this@MainActivity) { list ->
            bindind.textView.text = ""
            list.forEach {
                val word = "\b${it}\b"
                bindind.textView.append(word)
            }
        }

        //Подпишитесь на обновление таблицы, используя flow. Добавьте на экран текстовое поле,
// куда будут выводиться первые пять встречающихся слов.
        viewModel.allDictionarys.onEach {
            bindind.textView.text = it.toString()
        }.launchIn(lifecycleScope)


        bindind.add.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val inputText = bindind.textInput.text.toString()

                lifecycleScope.launch(Dispatchers.IO) {
                    fun numberOfRepetitionsNew(word: String): Int {
                        val a = viewModel.onContains(word)
                        var b = 0
                        if (a.isNotEmpty()) {
                            b = a.first().numberOfRepetitions
                        }
                        return b
                    }

                    val numberOfRepetitions = numberOfRepetitionsNew(inputText)
                    val dictionary = Dictionary(inputText, numberOfRepetitions)
                    val numberOfRepetitionsNew = numberOfRepetitions + 1
                    val dictionaryNew = Dictionary(inputText, numberOfRepetitionsNew)

                    lifecycleScope.launch(Dispatchers.IO) {
                        if (inputText.contains(Regex("""^[a-zA-Zа-яА-Я-]*$"""))) {
                            if (viewModel.onContains(dictionary.word).isEmpty()) {
                                viewModel.onAdd(dictionary)
                            } else viewModel.onUpdate(dictionaryNew)
                        } else lifecycleScope.launch(Dispatchers.Main) {
                            Toast.makeText(
                                applicationContext,
                                "Ошибка ввода, введите новое слово",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }

            bindind.clear.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.onDelete()
                }
            }
        }
    }
}













