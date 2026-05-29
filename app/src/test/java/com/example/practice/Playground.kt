package com.example.practice

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test

class Playground {

    @Test
    fun playground() = runTest {
        println("--- Playground Start ---")

        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        scope.launch {
            println("Launch started")

            runCatching {
                async {
                    error("boom")
                }
            }

            delay(1000)
            println("Still running?")
        }

        println("--- Playground End ---")
    }

    @Test
    fun coroutineMedium() = runBlocking {
        launch {
            delay(1500)
            println("1")
        }
        launch {
            Thread.sleep(2000)
            println("2")
        }
        println("3")
    }

    @Test
    fun coroutineHard() = runBlocking {
        val job = launch {
            launch {
                delay(1500)
                println("1: ${Thread.currentThread().name}")
            }

            launch {
                try {
                    Thread.sleep(2000)
                    println("2: ${Thread.currentThread().name}")
                } catch (e: InterruptedException) {
                    println("2: InterruptedException caught! $e")
                }
            }

            println("3: ${Thread.currentThread().name}")
            delay(1000)
        }

        delay(500)
        println("Cancelling job at ${System.currentTimeMillis()}")
        job.cancelAndJoin()
        println("Job cancelled at ${System.currentTimeMillis()}")
    }

    @Test
    fun main() {
        /*val input = intArrayOf(1, 2, 3, 3)
        val result = hasDuplicate(input)*/
        val result = isAnagram("racecar", "carrace")
        println(result)
    }

    fun hasDuplicate(nums: IntArray): Boolean {
        val hashMap = HashMap<Int, Int>()
        for (num in nums) {
            if (hashMap.containsKey(num)) {
                hashMap[num]?.let { hashMap[num] = it + 1 }
            } else {
                hashMap[num] = 1
            }
        }
        val map = hashMap.filterValues { value ->
            value > 1
        }
        return map.isNotEmpty()
    }

    fun isAnagram(s: String, t: String): Boolean {
        if (s.length != t.length) {
            return false
        }

        val counter = mutableMapOf<Char, Int>()
        for (char in s) {
            counter[char] = counter.getOrDefault(char, 0) + 1
        }

        for (char in t) {
            val count = counter.getOrDefault(char, 0)
            if (count == 0) return false
        }

        return true
    }

    fun twoSum(nums: IntArray, target: Int): IntArray {
        val newMap = HashMap<Int, Int>()
        nums.forEachIndexed { index, value ->
            newMap[value] = index
        }

        var result = intArrayOf()

        for (i in nums.indices) {
            val complement = target - nums[i]
            if (newMap.containsKey(complement) && newMap[complement] != i) {
                result = intArrayOf(i, newMap[complement]!!)
                return result
            }
        }
        return result
    }

    fun solution(arr: IntArray): Int {
        var ans = 1
        val hashset: Set<Int> = arr.toHashSet()
        while(hashset.contains(ans)){
           ans++
        }
        return ans
    }
}
