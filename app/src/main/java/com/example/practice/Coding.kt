package com.example.practice

fun main() {
    val input = intArrayOf(1, 2, 3, 4)
    val result = hasDuplicate(input)
    println(result)
}

fun hasDuplicate(nums: IntArray): Boolean {
    val hashMap = HashMap<Int, Int>()
    for(num in nums){
        if (hashMap.containsKey(num)) {
            hashMap[num] = hashMap[num]?.plus(1) as Int
        } else {
            hashMap[num] = 1
        }
    }
    val map = hashMap.filterValues { value ->
        value > 1
    }
    return map.size > 1
}