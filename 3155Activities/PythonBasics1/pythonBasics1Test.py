
import pythonBasics1
# main() is already set up to call the functions
# we want to test with a few different inputs,
# printing 'OK' when each function is correct.
# the simple provided test() function used in main() to print
# what each function returns vs. what it's supposed to return.

def test(got, expected):
    if got == expected:
        prefix = ' OK '
    else:
        prefix = '  X '
    print ('%s got: %s expected: %s' % (prefix, repr(got), repr(expected)))

# Calls the functions in pythonBasics1 with interesting inputs.
def main():
    # set which functions to test
    check_count_char = True
    check_is_power_of = False
    check_longest_word = False

    if check_count_char:
        print('Testing count_char:')
        test(pythonBasics1.count_char('vest','v'), 1)
        test(pythonBasics1.count_char('crypt','c'), 1)
        test(pythonBasics1.count_char('#python##','#'), 3)
        test(pythonBasics1.count_char('hello','H'), 0)
        test(pythonBasics1.count_char('',' '), 0)
        test(pythonBasics1.count_char('123',''), 0)
        test(pythonBasics1.count_char('',''), 0)
        test(pythonBasics1.count_char(' ',' '), 1)
        test(pythonBasics1.count_char('1211211','1'), 5)
        test(pythonBasics1.count_char('Abracadabra','a'), 4)
        test(pythonBasics1.count_char('pXXXp','X'), 3)
        test(pythonBasics1.count_char(' 6 6',' '), 2)

    if check_is_power_of:
        print("---------------------------------------------------------")
        print('Testing is_power_of:')
        test(pythonBasics1.is_power_of(1,1), True)
        test(pythonBasics1.is_power_of(10,1), True)
        test(pythonBasics1.is_power_of(1000,1), True)
        test(pythonBasics1.is_power_of(2,16), True)
        test(pythonBasics1.is_power_of(4,16), True)
        test(pythonBasics1.is_power_of(4,17), False)
        test(pythonBasics1.is_power_of(3,81), True)
        test(pythonBasics1.is_power_of(3,-81), False)
        test(pythonBasics1.is_power_of(-2,16), True)
        test(pythonBasics1.is_power_of(-2,-16), False)
        test(pythonBasics1.is_power_of(-2,-8), True)
        test(pythonBasics1.is_power_of(3,0), False)
        test(pythonBasics1.is_power_of(0,0), True)

    if check_longest_word:
        print("-------------------------------------------------------")
        print('Testing longest_word')
        test(pythonBasics1.longest_word("This is a test"),"test")
        test(pythonBasics1.longest_word("Autumn is a second spring when every leaf is a flower"),"flower")
        test(pythonBasics1.longest_word("The truth springs from arguments amongst friends"),"arguments")
        test(pythonBasics1.longest_word("He who has a why to live for can bear almost any how"),"almost")
        test(pythonBasics1.longest_word("It is only those who do nothing who makes no mistake"),"mistake")
        test(pythonBasics1.longest_word(""),"")

if __name__ == '__main__':
  main()
