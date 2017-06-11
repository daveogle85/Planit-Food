export interface DayCard {
    date: number;
    mealList: string[];
}

// Mock some cards for now

const mealList = [
    'Beans and Mash',
    'Cats and Dogs',
    'Eggy Sandwich',
    'Munchies and water',
    'Pie and Chips',
    'Ham and eggs',
    'Hot pot',
    'Tea and crumpet',
    'Roast Kitty Cat',
    'Fish and Chips',
    'Sardines and Rice',
    'Roast Chicken with Eggs and Slippers and dog foot'
];

export const mockMealList = (): DayCard[] => {
    return mealList.map((m) => {
        return {
            date: Date.now(),
            mealList: [
                m
            ]
        };
    });
};
