import unittest

import pythonBasics2

# main() is already set up to call the functions
# we want to test with a few different inputs,
# printing 'OK' when each function is correct.
# the simple provided test() function used in main() to print
# what each function returns vs. what it's supposed to return.

class TestPythonBasicsTwo(unittest.TestCase):

    def test_count_threes(self):

        self.assertEqual(pythonBasics2.count_threes('033'), 3)

        self.assertEqual(pythonBasics2.count_threes('9369'), 9)

        self.assertEqual(pythonBasics2.count_threes('999'), 9)

        self.assertEqual(pythonBasics2.count_threes('30669636'), 6)

    def test_longest_consecutive_repeating_char(self):

        # Default cases

        self.assertEqual(pythonBasics2.longest_consecutive_repeating_char('aaa'), ['a'])

        self.assertEqual(pythonBasics2.longest_consecutive_repeating_char('abba'), ['b'])

        self.assertEqual(pythonBasics2.longest_consecutive_repeating_char('caaddda'), ['d'])

        self.assertEqual(pythonBasics2.longest_consecutive_repeating_char('aaaffftttt'), ['t'])

        self.assertEqual(pythonBasics2.longest_consecutive_repeating_char('aaababbacccca'), ['c'])

        self.assertEqual(pythonBasics2.longest_consecutive_repeating_char('ddabab'), ['d'])

        self.assertEqual(pythonBasics2.longest_consecutive_repeating_char('caac'), ['a'])

        # Multiple outputs

        self.assertEqual(set(pythonBasics2.longest_consecutive_repeating_char('caacc')), set(['a', 'c']))

        self.assertEqual(set(pythonBasics2.longest_consecutive_repeating_char('bbbaaaceeef')), set(['a', 'b', 'e']))

        self.assertEqual(set(pythonBasics2.longest_consecutive_repeating_char('abcddefgghij')), set(['d', 'g']))

        self.assertEqual(set(pythonBasics2.longest_consecutive_repeating_char('aabbbccddddefggghhhh')), set(['d', 'h']))

    def test_is_palindrome(self):

        self.assertEqual(pythonBasics2.is_palindrome("Hello"), False)

        self.assertEqual(pythonBasics2.is_palindrome("civic"), True)

        self.assertEqual(pythonBasics2.is_palindrome("Civic"), True)

        self.assertEqual(pythonBasics2.is_palindrome("Racecar"), True)

        self.assertEqual(pythonBasics2.is_palindrome("Dont nod"), True)

        self.assertEqual(pythonBasics2.is_palindrome("was it a cat I saw"), True)

        self.assertEqual(pythonBasics2.is_palindrome("It was not a cat"), False)

if __name__ == '__main__':

  unittest.main(verbosity=1)
