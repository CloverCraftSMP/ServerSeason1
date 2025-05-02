ItemEvents.modification(event => {
    var modifyItems = function (modId, ids, func) {
        for (var itemId of ids) {
            event.modify(modId + ':' + itemId, func);
        }
    }
    var removeEdible = function (modId, ids) {
        modifyItems(modId, ids, item => {
            item.foodProperties = null;
        });
    }

    removeEdible("pamhc2foodcore", [
        "freshwateritem",
        "freshmilkitem",
        "saltitem",
        "saltandpepperitem",
        "cocoapowderitem",
        "pepperitem",
        "batteritem",
        "butteritem",
        "mayonaiseitem"
    ]);

    var items = Ingredient.of("@pamhc2crops").getItemIds();
    items = items.concat(Ingredient.of("@pamhc2foodcore").getItemIds());
    for (var itemId of items){
        if (!itemId.endsWith("seeditem")) {
            continue;
        }
        event.modify(itemId, (item) => item.foodProperties = null);
    }
});