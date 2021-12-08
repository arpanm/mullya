import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './stock.reducer';
import { IStock } from 'app/shared/model/stock.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Stock = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const stockList = useAppSelector(state => state.stock.entities);
  const loading = useAppSelector(state => state.stock.loading);
  const totalItems = useAppSelector(state => state.stock.totalItems);
  const links = useAppSelector(state => state.stock.links);
  const entity = useAppSelector(state => state.stock.entity);
  const updateSuccess = useAppSelector(state => state.stock.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="stock-heading" data-cy="StockHeading">
        <Translate contentKey="mullyaApp.stock.home.title">Stocks</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="mullyaApp.stock.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="mullyaApp.stock.home.createLabel">Create new Stock</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {stockList && stockList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="mullyaApp.stock.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('minPrice')}>
                    <Translate contentKey="mullyaApp.stock.minPrice">Min Price</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('maxPrice')}>
                    <Translate contentKey="mullyaApp.stock.maxPrice">Max Price</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('quantityKg')}>
                    <Translate contentKey="mullyaApp.stock.quantityKg">Quantity Kg</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('expiry')}>
                    <Translate contentKey="mullyaApp.stock.expiry">Expiry</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('avialableFrom')}>
                    <Translate contentKey="mullyaApp.stock.avialableFrom">Avialable From</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="mullyaApp.stock.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('stockStatus')}>
                    <Translate contentKey="mullyaApp.stock.stockStatus">Stock Status</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isOpenForBidding')}>
                    <Translate contentKey="mullyaApp.stock.isOpenForBidding">Is Open For Bidding</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="mullyaApp.stock.createdOn">Created On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="mullyaApp.stock.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="mullyaApp.stock.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="mullyaApp.stock.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mullyaApp.stock.farmerAddress">Farmer Address</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mullyaApp.stock.farmer">Farmer</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mullyaApp.stock.category">Category</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mullyaApp.stock.variant">Variant</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mullyaApp.stock.subVariant">Sub Variant</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {stockList.map((stock, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`${match.url}/${stock.id}`} color="link" size="sm">
                        {stock.id}
                      </Button>
                    </td>
                    <td>{stock.minPrice}</td>
                    <td>{stock.maxPrice}</td>
                    <td>{stock.quantityKg}</td>
                    <td>{stock.expiry}</td>
                    <td>{stock.avialableFrom}</td>
                    <td>{stock.description}</td>
                    <td>
                      <Translate contentKey={`mullyaApp.StockStatus.${stock.stockStatus}`} />
                    </td>
                    <td>{stock.isOpenForBidding ? 'true' : 'false'}</td>
                    <td>{stock.createdOn ? <TextFormat type="date" value={stock.createdOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>{stock.createdBy}</td>
                    <td>{stock.updatedOn ? <TextFormat type="date" value={stock.updatedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>{stock.updatedBy}</td>
                    <td>{stock.farmerAddress ? <Link to={`address/${stock.farmerAddress.id}`}>{stock.farmerAddress.id}</Link> : ''}</td>
                    <td>{stock.farmer ? <Link to={`actor/${stock.farmer.id}`}>{stock.farmer.id}</Link> : ''}</td>
                    <td>{stock.category ? <Link to={`catalogue/${stock.category.id}`}>{stock.category.id}</Link> : ''}</td>
                    <td>{stock.variant ? <Link to={`catalogue/${stock.variant.id}`}>{stock.variant.id}</Link> : ''}</td>
                    <td>{stock.subVariant ? <Link to={`catalogue/${stock.subVariant.id}`}>{stock.subVariant.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${stock.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${stock.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${stock.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="mullyaApp.stock.home.notFound">No Stocks found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Stock;
