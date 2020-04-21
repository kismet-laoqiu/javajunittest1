pragma solidity ^ 0.4 .10;
contract Account {

    struct Entity {
        string Id;
        string Type;
        string Name;
        uint Rest;
    }

    mapping(string => Entity) entityMap;


    function query(string id) returns(string Id, string Type, string Name, uint Rest) {
        Entity entity = entityMap[id];
        return (entity.Id, entity.Type, entity.Name, entity.Rest);
    }


    function createEntity(string Id, string Type, string Name, uint Rest) {
        entityMap[Id] = Entity(Id, Type, Name, Rest);
    }

    function issue(string Id, uint Money) {
        Entity entity = entityMap[Id];
        entity.Rest = entity.Rest + Money;
        entityMap[Id] = entity;
    }

    function transfer(string Id1, string Id2, uint Money) {
        Entity entity1 = entityMap[Id1];
        Entity entity2 = entityMap[Id2];
        entity1.Rest = entity1.Rest - Money;
        entity2.Rest = entity2.Rest + Money;
        entityMap[Id1] = entity1;
        entityMap[Id2] = entity2;
    }

}